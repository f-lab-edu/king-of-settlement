package son.kingofsettlement.user.dto;

public record SignUpRequest(String email, String password, String nickname) {

	// public boolean emailValidate(String email) {
	// 	return email.matches("");
	// }
	//
	// public boolean passwordValidate(String password) {
	// 	return password.matches("");
	// }
	//
	// public boolean nicknameValidate(String nickname) {
	// 	return nickname.matches("");
	// }
}
