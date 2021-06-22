#include <SPI.h>
#include <TimerOne.h>

SPISettings settings(8000000, MSBFIRST, SPI_MODE0);
const long tSampleInMicros = 1250; 
int value;
int i=1000;

void setup() {
  SPI.begin();
  SPI.beginTransaction(settings);
  pinMode(10, OUTPUT);
  digitalWrite(10, HIGH);


  Timer1.initialize(tSampleInMicros);
  Timer1.attachInterrupt(measureAndSend);

  Serial.begin(57600);
}

void loop() {
} 

int getEKGADC() {
  digitalWrite(10,LOW);
  value =SPI.transfer16(0x00);
  digitalWrite(10,HIGH);
  return (value);
  }

void measureAndSend(){
Serial.print(getEKGADC());
Serial.print("A");
  }

void serialportbuffertest(){
value++;
if(value ==4000){
  value=0;}
Serial.print(value);
Serial.print("A");
 }
