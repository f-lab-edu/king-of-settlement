package son.kingofsettlement.user.dto;

import son.kingofsettlement.user.exception.SignUpException;

public record SignUpRequest(String email, String password, String nickname) {

	public SignUpRequest(String email, String password, String nickname) {
		try {
			emailValidate(email);
			passwordValidate(password);
			nicknameValidate(nickname);
		} catch (Exception e) {
			String resultMessage = e.getMessage();
			throw new SignUpException(resultMessage);
		} finally {
			this.email = email;
			this.password = password;
			this.nickname = nickname;
		}
	}

	public void emailValidate(String email) throws Exception {
		if (!email.matches("[a-z_\\-.A-Z0-9]+@[a-z_\\-A-Z]+.[a-z_\\-A-Z]+")) {
			throw new Exception("password invalid : 이메일 형식을 확인해주세요");
		}
	}

	public void passwordValidate(String password) throws Exception {
		// 8~16자의 영문 대소문자, 숫자, 특수문자만
		// 공백 사용불가
		if (password.contains(" ")) {
			throw new Exception("password invalid : 공백을 포함할수 없습니다.");
		}
		if (!password.matches("[^\s]{8,16}")) {
			throw new Exception("password invalid : 8자 이상 16자 이하의 비밀번호를 입력해주세요");
		}
		int length = password.length();
		String s = password.replaceAll("[A-Z]", "");
		// 대문자 여부 확인
		if (s.length() == length) {
			throw new Exception("password invalid : 패스워드에 대문자를 포함해주세요");
		}
		String s1 = s.replaceAll("[a-z]", "");
		// 소문자 여부확인
		if (s1.length() == s.length()) {
			throw new Exception("password invalid : 패스워드에 소문자를 포함해주세요");
		}
		String s2 = s1.replaceAll("[0-9]", "");
		// 숫자 포함여부확인
		if (s2.length() == s1.length()) {
			throw new Exception("password invalid : 패스워드에 숫자를 포함해주세요");
		}
		String s3 = s2.replaceAll("[!\"#$%&'()*+,\\-./:;<=>?@\\[₩\\]^_`{|}~]", "");
		// 특수문자여부확인
		if (s3.length() == s2.length()) {
			throw new Exception("password invalid : 패스워드에 특수문자를 포함해주세요");
		}
	}

	public void nicknameValidate(String nickname) throws Exception {
		if (!nickname.matches("[a-z0-9A-Zㄱ-ㅎ가-힣]{2,10}")) {
			throw new Exception("password invalid : 닉네임을 확인해주세요");
		}
	}
}
