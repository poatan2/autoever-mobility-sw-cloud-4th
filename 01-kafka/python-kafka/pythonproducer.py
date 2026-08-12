from kafka import KafkaProducer
import json

# 프로듀서 클래스
class MessageProducer:

    #프로듀서 초기화
    def __init__(self, broker, topic):  # broker : broker의 위치, topic : topic 이름
        self.broker = broker
        self.topic = topic
        self.producer = KafkaProducer(
            bootstrap_servers = self.broker,
            value_serializer = lambda x:json.dumps(x).encode("utf-8"),
            acks=0,
            api_version=(2,5,0),
            key_serializer=str.encode,
            retries=3
        )

    def send_message(self, msg, auto_close=True):
        try:
            # send()는 브로커로 패킷을 전송하는 것이 아닌 메모리 버퍼에 전송해서 담아둠
            future = self.producer.send(self.topic, value=msg, key="key")
            # 실제 전송은 버퍼가 가득차거나 flush를 호출해야 브로커로 전송된다.
            self.producer.flush()
            if auto_close:
                self.producer.close()
            future.get(timeout=2)
            return {"status_code":200, "error":None}


        except Exception as exc:
            raise exc


#브로커와 토픽명을 지정
broker = ["localhost:9092"]
topic = "exam-topic"
pd = MessageProducer(broker, topic)

msg = {"name": "hong", "age": 50}
res = pd.send_message(msg)
print(res)