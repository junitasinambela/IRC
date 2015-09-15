/*
 * namespace dan bahasa service yang akan diimplementasikan
 */
namespace java if4031

typedef string String;

struct M{
	1: String nickname,
	2: String channel,
	3: String message,
	4: String time
}

typedef M Message

service ChatSendService{
	String setNickname(1: String nickname),
	String joinChannel(1: String channel),
	String leaveChannel(1: String channel),
	String sendMessage(1: String message),
	String sendMessageTo(1: String channel, 2: String message),
	String exit(),
}

service ChatReceiveService{
	Message receiveMessage(),
}

/*
 * setNick(nick) -> string (nickname)
 * joinChannel(channel) -> status (ok, error, fail)
 * leaveChannel(channel) -> status (ok, error, fail)
 * sendMessage(message) -> string (message)
 * sendMessageTo(channel, message) -> string (message)
 * exit() -> status (ok, error, fail)
 * receiveMessage() -> Message
 */
