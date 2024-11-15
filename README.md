# 🚗 경주 맞춤형 관광지 코스 추천 서비스 - Backend  

[![Contributors](https://img.shields.io/badge/contributors-4-brightgreen)](#-기여자-contributors)  

<img width="1285" alt="스크린샷 2024-11-15 20 55 06" src="https://github.com/user-attachments/assets/54bb6ca3-677c-4873-96bd-4e7763733d83">  

이 프로젝트는 경주 지역의 관광지를 개인의 선호에 맞게 추천해주는 웹 서비스의 백엔드 애플리케이션입니다. 데이터베이스 설계, 추천 알고리즘 로직 구현, API 제공을 통해 안정적이고 효율적인 맞춤형 관광 서비스 환경을 제공합니다.  

---

## 🧑‍💻 기여자  
| 이름      | Github 프로필     | 역할            |  
|-----------|-----------------|-----------------|  
| 권민지     | [mjgwon24](https://github.com/mjgwon24) | frontend & backend & design |
| 김이현     | [lh7721004](https://github.com/lh7721004) | frontend & backend |  
| 전상은 | [wowddok99](https://github.com/wowddok99) | frontend & backend |  
| 전형주     | [kavu-multi](https://github.com/kavu-multi) | AI/ML Engineer |  

---

## 👩‍💻 주요 기능  
- **사용자 맞춤형 추천 API**: AI 기반 알고리즘을 통해 개인화된 관광지 코스를 추천합니다.  
- **데이터베이스 관리**: MySQL 및 JPA를 사용해 관광지 정보, 사용자 데이터, 후기 데이터를 효과적으로 관리합니다.  
- **RESTful API 설계**: 안정적이고 효율적인 클라이언트-서버 통신을 지원합니다.  
- **Swagger 문서화**: API 명세를 자동화하여 개발 편의성을 향상시킵니다.  

---

## 🛠️ 기술 스택  
- **Backend Framework**: Spring Boot  
- **Database**: MySQL  
- **ORM**: JPA (Hibernate)  
- **API Documentation**: Swagger  
- **Build Tool**: Gradle  

---

## 📁 프로젝트 구조  
```
tour-recommend-back  
├── src/main/java/ # Java 소스 코드  
│   ├── tour_recommend/tour_recommend_back/
│       ├── config/ # 환경 및 설정 관련 파일  
│       ├── controller/ # API 엔드포인트 정의  
│       ├── service/ # 비즈니스 로직  
│       ├── repository/ # 데이터베이스 접근 계층
│       ├── dto/ # 클라이언트와 데이터 전송을 위한 DTO 클래스  
│       └── etity/ # 데이터베이스 테이블과 매핑되는 JPA 엔티티 클래스  
├── src/main/resources/   
│   └── static/ # 정적 리소스  
├── build.gradle # 빌드 설정 파일  
├── settings.gradle # 프로젝트 설정 파일  
├── gradlew/gradlew.bat # Gradle 실행 파일  
└── .gitignore # Git 무시 파일  
```  

---

## 📝 설치 및 실행  

1. 저장소 클론  
   ```bash  
   git clone https://github.com/mjgwon24/tour-recommend-back.git  
   cd tour-recommend-back  
   ```  

2. Gradle 의존성 설치  
   ```bash  
   ./gradlew build  
   ```  

3. 애플리케이션 실행  
   ```bash  
   ./gradlew bootRun  
   ```  

4. API 문서 확인  
   - Swagger 문서: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)  

---

## 📬 문의  

프로젝트 관련 문의는 GitHub Issues 또는 아래 연락처로 보내주세요.  

- **Github:** [mjgwon24](https://github.com/mjgwon24)  
- **이메일:** alswlchlrh8@naver.com  
