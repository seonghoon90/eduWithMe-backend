## ✏ EduWithMe
```
EduWithMe는 모든 연령과 배경의 사용자들이 언제 어디서나 접근할 수 있는 개인 맞춤형 학습을 통해 성장하며,
선생님과 학생 간의 상호작용을 강화하고 학습 동기를 높이는 교육 플랫폼입니다.
```
### [🏫 EduWithMe 사이트 바로가기 📝](https://eduwithme.com)

<br>

## 📆 개발 기간
- 2024.07.17 - 2024.08.20

<br>

## 📌 주요 기능
- 방 생성 및 문제 출제
- 포인트 획득 및 랭킹 시스템
- 마이페이지 기능
- 채팅 기능
- AI 질문하기 

<br>

## 🤝 팀원 / 역할분담
| 홍준빈 | 신성훈 | 이지우 | 이세원 |
|:------:|:------:|:------:|:------:|
| [![홍준빈](https://github.com/Hongjunbin.png)](https://github.com/Hongjunbin) | [![신성훈](https://github.com/seonghoon90.png)](https://github.com/seonghoon90) | [![이지우](https://github.com/wldnfl.png)](https://github.com/wldnfl) | [![이세원](https://github.com/leesw1945.png)](https://github.com/leesw1945) |
| <span style="font-size: 0.5em;">• 클래스룸 CRUD/페이징 정렬<br>• 실시간 채팅 기능<br>• FE 구현<br>• API 연동<br>• BE 배포<br>• FE 배포<br>• CI/CD</span> | <span style="font-size: 0.5em;">• 문제게시판 CRUD/페이징 정렬<br>• 댓글 CRUD<br>• FE 구현<br>• API 연동<br>• FE 배포<br>• UT 피드백 수정<br>• CI/CD</span> | <span style="font-size: 0.5em;">• 프로필<br>• 비밀번호 수정<br>• 마이페이지 문제 조회/페이징 정렬<br>• FE 구현<br>• API 연동<br>• UT 피드백 수정</span> | <span style="font-size: 0.5em;">• 로그인/회원가입<br>• 카카오 로그인<br>• 이메일 인증<br>• FE 구현<br>• API 연동<br>• BE 배포</span> |


<br>

## ⚙️ 기술 스택
  
| Type           | Tech                                                                                                                                                                                                                                                                                                                                                                                                                                     | 
|----------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| IDE            | ![IntelliJ IDEA](https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)                                         
| Framework      | ![Spring](https://img.shields.io/badge/SpringBoot_3.3.2-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)                                                
| Language       | ![Java](https://img.shields.io/badge/java_JDK17-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)                                                       
| Database       | ![MySQL](https://img.shields.io/badge/mysql_8.0.28-4479A1.svg?style=for-the-badge&logo=mysql&logoColor=white)                                                       
| Cashing        | ![Redis](https://img.shields.io/badge/redis_7.2.5-FF4438?style=for-the-badge&logo=redis&logoColor=white)                                                              
| Message Broker | ![Kafka](https://img.shields.io/badge/kafka-231F20?style=for-the-badge&logo=apachekafka&logoColor=white)                                                              
| Live Chatting  | ![Websocket](https://img.shields.io/badge/websocket-000000?style=for-the-badge&logo=websocket&logoColor=white)  ![Stomp](https://img.shields.io/badge/stomp-000000?style=for-the-badge&logo=stomp&logoColor=white)                                                                                                                                          
| Tools          | ![GitHub](https://img.shields.io/badge/github-%23121011.svg?style=for-the-badge&logo=github&logoColor=white) ![Git](https://img.shields.io/badge/git-%23F05033.svg?style=for-the-badge&logo=git&logoColor=white) ![Docker](https://img.shields.io/badge/docker_6.0.16-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)                        
| Collaboration  | ![Notion](https://img.shields.io/badge/Notion-%23000000.svg?style=for-the-badge&logo=notion&logoColor=white) ![Figma](https://img.shields.io/badge/figma-%23F24E1E.svg?style=for-the-badge&logo=figma&logoColor=white) ![Slack](https://img.shields.io/badge/slack-4A154B?style=for-the-badge&logo=slack&logoColor=white)                         
| Code Editor    | ![Visual Studio Code](https://img.shields.io/badge/Visual%20Studio%20Code-0078d7.svg?style=for-the-badge&logo=visual-studio-code&logoColor=white)                     
| FrontEnd       | ![React](https://img.shields.io/badge/react-%2320232a.svg?style=for-the-badge&logo=react&logoColor=%2361DAFB) ![HTML5](https://img.shields.io/badge/html5-E34F26?style=for-the-badge&logo=html5&logoColor=white) ![css](https://img.shields.io/badge/css3-1572B6?style=for-the-badge&logo=css3&logoColor=white) ![Javascript](https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=white)        |
| Infra          | ![EC2](https://img.shields.io/badge/EC2-FF9900?style=for-the-badge&logo=amazonec2&logoColor=white) ![ELB](https://img.shields.io/badge/ELB-8C4FFF?style=for-the-badge&logo=awselasticloadbalancing&logoColor=white) ![Route53](https://img.shields.io/badge/Route53-8C4FFF?style=for-the-badge&logo=amazonroute53&logoColor=white) ![Route53](https://img.shields.io/badge/nginx-009639?style=for-the-badge&logo=nginx&logoColor=white)  |

<br>

## 🌌 환경변수
```
spring.data.redis.host=${REDIS_HOST}
spring.datasource.url=${MYSQL_URL}
spring.datasource.username=${MYSQL_ROOT_USER}
spring.datasource.password=${MYSQL_ROOT_PASSWORD}
jwt.secret.key=${JWT_SECRET_KEY}
email.fromEmail=${FROM_EMAIL}
email.emailPassword=${EMAIL_PASSWORD}
client.id=${CLIENT_ID}
kakao.init=${KAKAO_INIT}
redirect.uri=${REDIRECT_URI}
cloud.aws.credentials.accessKey=${ACCESS_KEY}
cloud.aws.credentials.secretKey=${SECRET_KEY}
cloud.aws.s3.bucketName=${BUCKET_NAME}
cloud.aws.region.static=${REGION}
frontend.domain=${FRONTEND_DOMAIN}
frontend.kakao.domain=${FRONTEND_KAKAO_DOMAIN}
gemini.api.url=${GEMINI_URL}
gemini.api.key=${GEMINI_KEY}
```
## 📚 기술 문서
<details>
<summary>🌠 Commit Rule</summary>
<div markdown="1">
  
## 🌠 Commit Rule

| 작업 타입 | 작업내용 |
| --- | --- |
| ✨ feature | 새로운 기능을 추가 |
| 🐛 bugfix | 버그 수정 |
| ♻️ refactor | 코드 리팩토링 |
| 🩹 fix | 코드 수정 |
| 🚚 move | 파일 옮김/정리 |
| 🔥 del | 기능/파일을 삭제 |
| 💄 style | css |
| 🍻 test | 테스트 코드를 작성 |
| 🎨 readme | readme 수정 |
| 🙈 gitfix | gitignore 수정 |
| 🔨script | package.json 변경(npm 설치 등) |


</div>
</details>

<details>
<summary> 💻 아키텍처</summary>
<div markdown="1">

## 💻 아키텍처
 ![아키텍처](https://github.com/user-attachments/assets/db0585d7-d4cd-4c92-bc9e-21a8cfa9dd66)

</div>
</details>

<br>


## 🖥 와이어프레임
![eduWithMeWireFrame](https://github.com/user-attachments/assets/3d84ec7f-11fd-400e-9eea-f25ca0e25c66)


<br>


## 📊 ERD DIAGRAM
![image](https://github.com/user-attachments/assets/2aae0197-3ec8-4676-a02d-fcfb8576ad86)


<br>


## 📝 API 명세서
[🔗 EduWithMe API 명세서](https://luxuriant-volcano-e6c.notion.site/f676cf01326644cfba35e2abfce1427e?v=012d2fa6f689494eb20d90d1d0b7aa04)

<br>
