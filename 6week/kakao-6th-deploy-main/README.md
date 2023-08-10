# deploy

## 1. settings.gradle 
```groovy
rootProject.name = 'kakao'
```

## 2. build.gradle
```groovy
version = '1.0'

tasks.named('test') {
    // 생략
    systemProperty 'file.encoding', 'UTF-8'
    // 생략
}

jar {
	enabled = false
}
```

## 3. Dockerfile 생성

## 4.startFront.sh 생성