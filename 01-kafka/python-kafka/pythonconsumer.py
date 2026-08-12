from kafka import KafkaConsumer
import json

class MessageConsumer:
    def __init__(self, broker, topic):
        self.broker = broker
        self.consumer = KafkaConsumer(
            topic,
            bootstrap_servers= self.broker,
            value_deserializer= lambda x:x.decode("utf-8"),
            group_id="my-group",
            auto_offset_reset= "earliest",
            enable_auto_commit= True
        )

    def receive_message(self):
        try:
            for message in self.consumer:
                print(message.value)
        except Exception as exc:
            raise exc
    
broker =["localhost:9092"]
topic = "exam-topic"

cs = MessageConsumer(broker, topic)
cs.receive_message()