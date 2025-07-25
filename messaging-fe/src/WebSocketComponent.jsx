import { useState, useEffect } from "react";

const WebSocketComponent = () => {
  const [message, setMessage] = useState("");
  const [messages, setMessages] = useState([]);
  const [socket, setSocket] = useState(null);

  useEffect(() => {
    const ws = new WebSocket("ws://localhost:5000/ws");
    setSocket(ws);

    ws.onmessage = (event) => {
      const parsedMessage = JSON.parse(event.data);
      setMessages((prevMessages) => [...prevMessages, parsedMessage]);
    };

    return () => {
      ws.close();
    };
  }, []);

  const sendMessage = () => {
    if (message.trim() !== "" && socket) {
      const messageObject = { content: message };
      socket.send(JSON.stringify(messageObject));
      setMessage("");
    }
  };

  const handleKeyDown = (event) => {
    if (event.key === "Enter") {
      event.preventDefault();
      sendMessage();
    }
  };

  return (
    <div className="flex flex-col h-screen bg-gray-100">
      <h1 className="text-2xl font-bold bg-blue-800 text-gray-200 py-4 px-8 mb-4">
        Messaging App
      </h1>
      <div className="flex-grow overflow-y-auto p-4">
        <div className="space-y-2">
          {messages.map((msg, index) => (
            <div key={index} className="bg-white p-2 rounded shadow-sm">
              {msg.content}
            </div>
          ))}
        </div>
      </div>

      <div className="p-4 bg-white border-t">
        <input
          type="text"
          value={message}
          onChange={(e) => setMessage(e.target.value)}
          onKeyDown={handleKeyDown}
          className="w-full p-2 border rounded"
          placeholder="Type your message..."
        />
        <button
          onClick={sendMessage}
          className="mt-2 bg-blue-600 text-white py-2 px-4 rounded"
        >
          Send
        </button>
      </div>
    </div>
  );
};

export default WebSocketComponent;
