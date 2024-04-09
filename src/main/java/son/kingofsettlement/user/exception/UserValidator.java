package son.kingofsettlement.user.exception;

import jakarta.validation.ValidationException;

public class UserValidator {
	public static void validateNickname(String nickname) throws ValidationException {
		if (!nickname.matches("[a-z0-9A-Zㄱ-ㅎ가-힣]{2,10}")) {
			throw new ValidationException("닉네임을 확인해주세요");
		}
	}

	public static void validatePassword(String password) throws ValidationException {
		// 공백 포함여부확인
		if (password.contains(" ")) {
			throw new ValidationException("패스워드에 공백을 포함할수 없습니다.");
		}
		// 글자수 확인 8~16자 이내
		if (!password.matches("\\S{8,16}")) {
			throw new ValidationException("8자 이상 16자 이하의 비밀번호를 입력해주세요");
		}
		int length = password.length();
		String s = password.replaceAll("[A-Z]", "");
		// 대문자 포함여부확인
		if (s.length() == length) {
			throw new ValidationException("패스워드에 대문자를 포함해주세요");
		}
		String s1 = s.replaceAll("[a-z]", "");
		// 소문자 포함여부확인
		if (s1.length() == s.length()) {
			throw new ValidationException("패스워드에 소문자를 포함해주세요");
		}
		String s2 = s1.replaceAll("[0-9]", "");
		// 숫자 포함여부확인
		if (s2.length() == s1.length()) {
			throw new ValidationException("패스워드에 숫자를 포함해주세요");
		}
		String s3 = s2.replaceAll("[!\"#$%&'()*+,\\-./:;<=>?@\\[₩\\]^_`{|}~]", "");
		// 특수문자 포함여부확인
		if (s3.length() == s2.length()) {
			throw new ValidationException("패스워드에 특수문자를 포함해주세요");
		}
	}
}

