/*
 * namespace dan bahasa service yang akan diimplementasikan
 */
namespace java if4031

typedef string String

typedef i32 int

struct M{
	1: String nickname,
	2: String channel,
	3: String message,
	4: String time
}

typedef M Message

service ChatSendService{
	int setNickname(1: String nickname),
	int joinChannel(1: String channel),
	int leaveChannel(1: String channel),
	int sendMessage(1: String message),
	int sendMessageTo(1: String channel, 2: String message),
	int exit(),
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
