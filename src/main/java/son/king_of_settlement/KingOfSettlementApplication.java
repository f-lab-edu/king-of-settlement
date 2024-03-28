package son.king_of_settlement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // Spring Boot 애플리케이션의 주 클래스에 붙이며, 구성(Configuration), 자동 구성(Auto-configuration), 컴포넌트 스캔을 단순하게 처리해준다. 이를 통해 애플리케이션의 설정과 시작을 편리해짐.
public class KingOfSettlementApplication {

	public static void main(String[] args) {
		SpringApplication.run(KingOfSettlementApplication.class, args);
	}

}