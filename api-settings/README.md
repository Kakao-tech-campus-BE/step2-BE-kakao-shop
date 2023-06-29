# KAKAO 쇼핑

## 로컬에서 실행 파일 만들기
```text
host : localhost:8080


1. git bash를 열어주세요. Mac 사용자는 터미널을 이용해서 해당 경로로 가세요.
2. ./gradlew clean build
3. 윈도우 사용자는 git bash를 이용하여 터미널을 열고 3번 명령어를 실행해주세요.
4. cd build/libs
5. 위 경로에 kakao-1.0.jar파일이 생성됩니다
6. jar 파일 동일 경로에 images 폴더를 카피 한뒤 아래 명령어로 실행해주세요
7. images폴더는 kakao-shop-api/images 경로에 있습니다.
8. java -jar -Dspring.profiles.active=local kakao-1.0.jar
```

## 사진 접근하는 법
- localhost:8080/images/1.jpg