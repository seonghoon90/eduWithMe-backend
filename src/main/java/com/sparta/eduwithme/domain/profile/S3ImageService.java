package com.sparta.eduwithme.domain.profile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.sparta.eduwithme.common.exception.CustomException;
import com.sparta.eduwithme.common.exception.ErrorCode;
import com.sparta.eduwithme.domain.user.entity.User;
import com.sparta.eduwithme.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3ImageService {

    private final AmazonS3 amazonS3;
    private final ProfileRepository profileRepository;

    @Value("${cloud.aws.s3.bucketName}")
    private String bucketName;
    
    // 컨트롤러로 부터 이미지 받아오는 메서드
    public String upload(MultipartFile image, Long userId) {
        //입력받은 이미지 파일이 빈 파일인지 검증
        if(image.isEmpty() || Objects.isNull(image.getOriginalFilename())){
            throw new CustomException(ErrorCode.EMPTY_FILE);
        }
        //uploadImage 호출하여 S3에 저장된 이미지의 public url을 반환한다.
        return this.uploadImage(image, userId);
    }
    
    // 이미지 업로드 메서드
    private String uploadImage(MultipartFile image, Long userId) {
        this.validateImageFileExtention(image.getOriginalFilename()); // 확장자 검증
        try {
            return this.uploadImageToS3(image, userId); // 이미지 업로드 메서드
        } catch (IOException e) {
            throw new CustomException(ErrorCode.IO_EXCEPTION_UPLOAD); // 업로드 I/O 예외
        }
    }
    
    // 확장자 검증하는 메서드
    private void validateImageFileExtention(String filename) {
        int lastDotIndex = filename.lastIndexOf("."); // "."의 위치를 정수로 반환
        if (lastDotIndex == -1) { // -1 : 파일 이름에 확장자가 없는 경우 ( .이 존재하지 않는 경우 )
            throw new CustomException(ErrorCode.NO_EXTENSION);
        }
        // . 뒤의 확장자 소문자로 변경 후 자르기
        String extention = filename.substring(lastDotIndex + 1).toLowerCase();
        List<String> allowedExtentionList = Arrays.asList("jpg", "jpeg", "png", "gif");
        // 받아온 확장자 비교
        if (!allowedExtentionList.contains(extention)) {
            throw new CustomException(ErrorCode.INVALID_EXTENSION);
        }
    }

    private String uploadImageToS3(MultipartFile image, Long userId) throws IOException {
        // 원본 파일명 가져오기
        String originalFilename = image.getOriginalFilename();

        // 파일의 확장자 명 추출
        String extention = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        // S3에 저장할 파일명 생성 (UUID를 이용해 고유한 파일명 생성)
        String s3FileName = UUID.randomUUID().toString().substring(0, 10) + originalFilename;

        // 파일의 InputStream을 가져오기
        InputStream is = image.getInputStream();
        byte[] bytes = IOUtils.toByteArray(is);  // InputStream을 byte 배열로 변환

        // S3 객체 메타데이터 설정
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("image/" + extention); // 이미지의 MIME 타입 설정
        metadata.setContentLength(bytes.length); // 파일의 크기 설정
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        log.info(bucketName);
        try {
            // S3에 파일 업로드 요청 설정
            PutObjectRequest putObjectRequest =
                    new PutObjectRequest(bucketName, s3FileName, byteArrayInputStream, metadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead); // 공개 접근 설정
            // S3에 파일 업로드
            amazonS3.putObject(putObjectRequest);

            // 사용자 프로필 사진 URL 업데이트
            User user = profileRepository.findById(userId).orElseThrow(() ->
                    new CustomException(ErrorCode.USER_NOT_FOUND));
            user.updatePhotoUrl(amazonS3.getUrl(bucketName, s3FileName).toString());
            profileRepository.save(user);
        } catch (Exception e) {
            // 예외 발생 시 커스텀 예외 처리
            throw new CustomException(ErrorCode.PUT_OBJECT_FAILURE);
        } finally {
            // 리소스 해제
            byteArrayInputStream.close();
            is.close();
        }

        // 업로드된 파일의 공개 URL 반환
        return amazonS3.getUrl(bucketName, s3FileName).toString();
    }

    public void deleteImageFromS3(String imageAddress){
        String key = getKeyFromImageAddress(imageAddress);
        try{
            amazonS3.deleteObject(new DeleteObjectRequest(bucketName, key));
        }catch (Exception e){
            throw new CustomException(ErrorCode.IO_EXCEPTION_DELETE);
        }
    }

    private String getKeyFromImageAddress(String imageAddress){
        try{
            URL url = new URL(imageAddress);
            String decodingKey = URLDecoder.decode(url.getPath(), "UTF-8");
            return decodingKey.substring(1); // 맨 앞의 '/' 제거
        }catch (MalformedURLException | UnsupportedEncodingException e){
            throw new CustomException(ErrorCode.IO_EXCEPTION_DELETE);
        }
    }
}