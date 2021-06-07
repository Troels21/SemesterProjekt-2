#include <SPI.h>
#include <TimerOne.h>

SPISettings settings(8000000, MSBFIRST, SPI_MODE0);
const long tSampleInMicros = 1265; 
int value;

void setup() {
  SPI.begin();
  SPI.beginTransaction(settings);
  pinMode(10, OUTPUT);
  digitalWrite(10, HIGH);


  Timer1.initialize(tSampleInMicros);
  Timer1.attachInterrupt(measureAndSend);

  Serial.begin(19200);
}

void loop() {
} 

int getEKGADC() {
  digitalWrite(10,LOW);
  value =SPI.transfer16(0x00);
  digitalWrite(10,HIGH);
  return (value+100);
  }

void measureAndSend(){
Serial.println(getEKGADC());
  }
