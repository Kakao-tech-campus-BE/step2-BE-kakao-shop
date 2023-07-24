package com.example.kakao;

import com.example.kakao.product.Product;
import com.example.kakao.product.ProductJPARepository;
import com.example.kakao.product.option.Option;
import com.example.kakao.product.option.OptionJPARepository;
import com.example.kakao.user.User;
import com.example.kakao.user.UserJPARepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;


@SpringBootApplication
public class KakaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(KakaoApplication.class, args);
	}

	@Profile("local")
	@Bean
	CommandLineRunner localServerStart(UserJPARepository userJPARepository, ProductJPARepository productJPARepository, OptionJPARepository optionJPARepository, PasswordEncoder passwordEncoder){
		return args -> {
			userJPARepository.saveAll(Arrays.asList(newUser("ssarmango", passwordEncoder)));
			productJPARepository.saveAll(Arrays.asList(
					newProduct("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", 1, 1000),
					newProduct("[황금약단밤 골드]2022년산 햇밤 칼집밤700g외/군밤용/생율", 2, 2000),
					newProduct("삼성전자 JBL JR310 외 어린이용/성인용 헤드셋 3종!", 3, 30000),
					newProduct("바른 누룽지맛 발효효소 2박스 역가수치보장 / 외 7종", 4, 4000),
					newProduct("[더주] 컷팅말랑장족, 숏다리 100g/300g 외 주전부리 모음 /중독성 최고/마른안주", 5, 5000),
					newProduct("굳지않는 앙금절편 1,050g 2팩 외 우리쌀떡 모음전", 6, 15900),
					newProduct("eoe 이너딜리티 30포, 오렌지맛 고 식이섬유 보충제", 7, 26800),
					newProduct("제나벨 PDRN 크림 2개. 피부보습/진정 케어", 8, 25900),
					newProduct("플레이스테이션 VR2 호라이즌 번들. 생생한 몰입감", 9, 797000),
					newProduct("통영 홍 가리비 2kg, 2세트 구매시 1kg 추가증정", 10, 8900),
					newProduct("아삭한 궁채 장아찌 1kg 외 인기 반찬 모음전", 11, 6900),
					newProduct("깨끗한나라 순수소프트 30롤 2팩. 무형광, 도톰 3겹", 12, 28900),
					newProduct("생활공작소 초미세모 칫솔 12입 2개+가글 증정", 13, 9900),
					newProduct("경북 영천 샤인머스켓 가정용 1kg 2수 내외", 14, 9900),
					newProduct("[LIVE][5%쿠폰] 홈카페 Y3.3 캡슐머신 베이직 세트", 15, 148000)
			));
			optionJPARepository.saveAll(Arrays.asList(
					newOption(Product.builder().id(1).build(), "01. 슬라이딩 지퍼백 크리스마스에디션 4종", 10000),
					newOption(Product.builder().id(1).build(), "02. 슬라이딩 지퍼백 플라워에디션 5종", 10900),
					newOption(Product.builder().id(1).build(), "고무장갑 베이지 S(소형) 6팩", 9900),
					newOption(Product.builder().id(1).build(), "뽑아쓰는 키친타올 130매 12팩", 16900),
					newOption(Product.builder().id(1).build(), "2겹 식빵수세미 6매", 8900),
					newOption(Product.builder().id(2).build(), "22년산 햇단밤 700g(한정판매)", 9900),
					newOption(Product.builder().id(2).build(), "22년산 햇단밤 1kg(한정판매)", 14500),
					newOption(Product.builder().id(2).build(), "밤깎기+다회용 구이판 세트", 5500),
					newOption(Product.builder().id(3).build(), "JR310 (유선 전용) - 블루", 29900),
					newOption(Product.builder().id(3).build(), "JR310BT (무선 전용) - 레드", 49900),
					newOption(Product.builder().id(3).build(), "JR310BT (무선 전용) - 그린", 49900),
					newOption(Product.builder().id(3).build(), "JR310BT (무선 전용) - 블루", 49900),
					newOption(Product.builder().id(3).build(), "T510BT (무선 전용) - 블랙", 52900),
					newOption(Product.builder().id(3).build(), "T510BT (무선 전용) - 화이트", 52900),
					newOption(Product.builder().id(4).build(), "선택01_바른곡물효소 누룽지맛 2박스", 17900), //15
					newOption(Product.builder().id(4).build(), "선택02_바른곡물효소누룽지맛 6박스", 50000),
					newOption(Product.builder().id(4).build(), "선택03_바른곡물효소3박스+유산균효소3박스", 50000),
					newOption(Product.builder().id(4).build(), "선택04_바른곡물효소3박스+19종유산균3박스", 50000),
					newOption(Product.builder().id(5).build(), "01. 말랑컷팅장족 100g", 4900),
					newOption(Product.builder().id(5).build(), "02. 말랑컷팅장족 300g", 12800),
					newOption(Product.builder().id(5).build(), "03. 눌린장족 100g", 4900),
					newOption(Product.builder().id(6).build(), "굳지않는 쑥 앙금 절편 1050g", 15900),
					newOption(Product.builder().id(6).build(), "굳지않는 흑미 앙금 절편 1050g", 15900),
					newOption(Product.builder().id(6).build(), "굳지않는 흰 가래떡 1050g", 15900),
					newOption(Product.builder().id(7).build(), "이너딜리티 1박스", 26800), //25
					newOption(Product.builder().id(7).build(), "이너딜리티 2박스+사은품 2종", 49800),
					newOption(Product.builder().id(8).build(), "제나벨 PDRN 자생크림 1+1", 25900),
					newOption(Product.builder().id(9).build(), "플레이스테이션 VR2 호라이즌 번들", 839000),
					newOption(Product.builder().id(9).build(), "플레이스테이션 VR2", 797000),
					newOption(Product.builder().id(10).build(), "홍가리비2kg(50미이내)", 8900), //30
					newOption(Product.builder().id(11).build(), "궁채 절임 1kg", 6900),
					newOption(Product.builder().id(11).build(), "양념 깻잎 1kg", 8900),
					newOption(Product.builder().id(11).build(), "된장 깻잎 1kg", 8900),
					newOption(Product.builder().id(11).build(), "간장 깻잎 1kg", 7900),
					newOption(Product.builder().id(11).build(), "고추 무침 1kg", 8900),
					newOption(Product.builder().id(11).build(), "파래 무침 1kg", 9900),
					newOption(Product.builder().id(12).build(), "01_순수소프트 27m 30롤 2팩", 28900),
					newOption(Product.builder().id(12).build(), "02_벚꽃 프리미엄 27m 30롤 2팩", 32900),
					newOption(Product.builder().id(13).build(), "(증정) 초미세모 칫솔 12개 x 2개", 11900),
					newOption(Product.builder().id(13).build(), "(증정) 잇몸케어 치약 100G 3개 x 2개", 16900),
					newOption(Product.builder().id(13).build(), "(증정) 구취케어 치약 100G 3개 x 2개", 16900),
					newOption(Product.builder().id(13).build(), "(증정)화이트케어 치약 100G 3개 x 2개", 19900),
					newOption(Product.builder().id(13).build(), "(증정) 어린이 칫솔 12EA", 9900),
					newOption(Product.builder().id(14).build(), "[가정용] 샤인머스켓 1kg 2수내외", 9900),
					newOption(Product.builder().id(14).build(), "[특품] 샤인머스켓 1kg 1-2수", 12900), //45
					newOption(Product.builder().id(14).build(), "[특품] 샤인머스켓 2kg 2-3수", 23900),
					newOption(Product.builder().id(15).build(), "화이트", 148000),
					newOption(Product.builder().id(15).build(), "블랙", 148000)
			));
		};
	}

	private User newUser(String username, PasswordEncoder passwordEncoder){
		return User.builder()
				.email(username+"@nate.com")
				.password(passwordEncoder.encode("meta1234!"))
				.username(username)
				.roles(username.equals("admin") ? "ROLE_ADMIN" : "ROLE_USER")
				.build();
	}

	private Product newProduct(String productName, int imageNumber, int price){
		return Product.builder()
				.productName(productName)
				.description("")
				.image("/images/"+imageNumber+".jpg")
				.price(price)
				.build();
	}

	private Option newOption(Product product, String optionName, int price){
		return Option.builder()
				.product(product)
				.optionName(optionName)
				.price(price)
				.build();
	}
}
