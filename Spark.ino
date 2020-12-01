#include <ESP8266WiFi.h>
#include <FirebaseArduino.h>
//#include <StopWatch.h>

// Set these to run example.
#define FIREBASE_HOST "put your firebase host"
#define FIREBASE_AUTH "Authentication key"
#define WIFI_SSID "POCO"
#define WIFI_PASSWORD "########"

//StopWatch sw_secs(StopWatch::SECONDS);



// defines pins numbers
const int trigPin = 5;  //D1
const int echoPin = 4;  //D2
const int trigPin1 = 14;  //D5  
const int echoPin1 = 12;  //D6

const int trigPin2 = 2;  //D4
const int echoPin2 = 0;  //D3
const int trigPin3 = 15;  //D8
const int echoPin3 = 13;  //D7


// defines variables
long duration;
int distance;
long duration1;
int distance1;
long duration2;
int distance2;
long duration3;
int distance3;

void setup() {
  Serial.begin(9600);
pinMode(trigPin, OUTPUT); // Sets the trigPin as an Output
pinMode(echoPin, INPUT); // Sets the echoPin as an Input
pinMode(trigPin1, OUTPUT); // Sets the trigPin as an Output
pinMode(echoPin1, INPUT); // Sets the echoPin as an Input
pinMode(trigPin2, OUTPUT); // Sets the trigPin as an Output
pinMode(echoPin2, INPUT); // Sets the echoPin as an Input
pinMode(trigPin3, OUTPUT); // Sets the trigPin as an Output
pinMode(echoPin3, INPUT); // Sets the echoPin as an Input
  // connect to wifi.
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  Serial.print("connecting");
  while (WiFi.status() != WL_CONNECTED) {
    Serial.print(".");
    delay(500);
  }
  Serial.println();
  Serial.print("connected: ");
  Serial.println(WiFi.localIP());
  
  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
}

int n = 0;
int t = 0;
int t0 = 0;
int t1 = 0;
int t2 = 0;

void loop() {
//DISTANCE FOR SENSOR 1
// Clears the trigPin
digitalWrite(trigPin, LOW);
delayMicroseconds(2);

// Sets the trigPin on HIGH state for 10 micro seconds
digitalWrite(trigPin, HIGH);
delayMicroseconds(10);
digitalWrite(trigPin, LOW);

// Reads the echoPin, returns the sound wave travel time in microseconds
duration = pulseIn(echoPin, HIGH);

// Calculating the distance
distance= duration*0.034/2;
// Prints the distance on the Serial Monitor
Serial.print("Distance : ");
Serial.println(distance);
delay(1000);

//DISTANCE FOR SENSOR 2
digitalWrite(trigPin1, LOW);
delayMicroseconds(2);
// Sets the trigPin on HIGH state for 10 micro seconds
digitalWrite(trigPin1, HIGH);
delayMicroseconds(10);
digitalWrite(trigPin1, LOW);
// Reads the echoPin, returns the sound wave travel time in microseconds
duration1 = pulseIn(echoPin1, HIGH);
// Calculating the distance
distance1= duration1*0.034/2;
// Prints the distance on the Serial Monitor
Serial.print("Distance 1: ");
Serial.println(distance1);
delay(1000);

//DISTANCE FOR SENSOR 3
// Clears the trigPin
digitalWrite(trigPin2, LOW);
delayMicroseconds(2);

// Sets the trigPin on HIGH state for 10 micro seconds
digitalWrite(trigPin2, HIGH);
delayMicroseconds(10);
digitalWrite(trigPin2, LOW);

// Reads the echoPin, returns the sound wave travel time in microseconds
duration2 = pulseIn(echoPin2, HIGH);

// Calculating the distance
distance2= duration2*0.034/2;
// Prints the distance on the Serial Monitor
Serial.print("Distance 2 : ");
Serial.println(distance2);
delay(1000);

//DISTANCE FOR SENSOR 4
// Clears the trigPin
digitalWrite(trigPin3, LOW);
delayMicroseconds(2);

// Sets the trigPin on HIGH state for 10 micro seconds
digitalWrite(trigPin3, HIGH);
delayMicroseconds(10);
digitalWrite(trigPin3, LOW);

// Reads the echoPin, returns the sound wave travel time in microseconds
duration3 = pulseIn(echoPin3, HIGH);

// Calculating the distance
distance3= duration3*0.034/2;
// Prints the distance on the Serial Monitor
Serial.print("Distance 3 : ");
Serial.println(distance3);
delay(1000);


//FOR FIRST ULTRASONIC
if(distance<=10 && distance1<=10)
  {  
 
  // append a new value to /logs
    Firebase.set("data/QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3/account", "Owner");
    Firebase.set("data/QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3/car_standing/1/spot", "Yes");
    if(distance2<=10 && distance3<=10){
      Firebase.set("data/QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3/car_standing/2/spot", "Yes");  
      Firebase.set("data/QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3/car_standing/2/time", t1);
      t1=t1+1;
    }else {
      Firebase.set("data/QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3/car_standing/2/spot", "No");  
      Firebase.set("data/QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3/car_standing/2/time", t0);
    }
    
    Firebase.set("data/QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3/city", "V V Nagar");
    Firebase.set("data/QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3/cost", "2");
    Firebase.set("data/QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3/email", "amj@gmail.com");
    Firebase.set("data/QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3/gid", "akashjoshi61099@okaxis");
    Firebase.set("data/QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3/lat", 22.552613);
    Firebase.set("data/QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3/lng", 72.923816);
    Firebase.set("data/QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3/name", "Aakash joshi");
    Firebase.set("data/QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3/phone", "9687174843");
    Firebase.set("data/QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3/place_name", "BVM Engineering College");
    Firebase.set("data/QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3/image/image1", "https://media.gettyimages.com/photos/vacant-car-parking-space-picture-id173934806?s=612x612");
    Firebase.set("data/QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3/car_standing/1/time", t);
    t = t+1;
        if (Firebase.failed()) {
      Serial.print("pushing /logs failed:");
      Serial.println(Firebase.error());  
      return;
  }

  }

else if(distance2<=10 && distance3<=10)
{
  // append a new value to /logs
    Firebase.set("data/QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3/account", "Owner");
    if(distance<=10 && distance1<=10){
      Firebase.set("data/QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3/car_standing/1/spot", "Yes");
      Firebase.set("data/QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3/car_standing/1/time", t1);
      t1= t1+1;
    } else{
      Firebase.set("data/QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3/car_standing/1/spot", "No");
      Firebase.set("data/QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3/car_standing/1/time", t0);
    }
    
    Firebase.set("data/QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3/car_standing/2/spot", "Yes");
    Firebase.set("data/QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3/city", "V V Nagar");
    Firebase.set("data/QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3/cost", "2");
    Firebase.set("data/QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3/email", "amj@gmail.com");
    Firebase.set("data/QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3/gid", "akashjoshi61099@okaxis");
    Firebase.set("data/QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3/lat", 22.552613);
    Firebase.set("data/QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3/lng", 72.923816);
    Firebase.set("data/QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3/name", "Aakash joshi");
    Firebase.set("data/QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3/phone", "9687174843");
    Firebase.set("data/QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3/place_name", "BVM Engineering College");
    Firebase.set("data/QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3/image/image1", "https://media.gettyimages.com/photos/vacant-car-parking-space-picture-id173934806?s=612x612");
    Firebase.set("data/QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3/car_standing/2/time", t);
    t=t+1;
    //Firebase.set("Time", t*1000 );
        if (Firebase.failed()) {
      Serial.print("pushing /logs failed:");
      Serial.println(Firebase.error());  
      return;
  
}
  
  }

else if((distance2<=10 && distance3<=10) && (distance<=10 && distance1<=10))
{
  // append a new value to /logs
    Firebase.set("data/QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3/account", "Owner");
    Firebase.set("data/QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3/car_standing/1/spot", "Yes");
    Firebase.set("data/QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3/car_standing/1/time", t1);
    t1=t1+1;
    Firebase.set("data/QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3/car_standing/2/spot", "Yes");
    Firebase.set("data/QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3/car_standing/1/time", t2);
    t2=t2+1;
    Firebase.set("data/QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3/city", "V V Nagar");
    Firebase.set("data/QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3/cost", "2");
    Firebase.set("data/QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3/email", "amj@gmail.com");
    Firebase.set("data/QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3/gid", "akashjoshi61099@okaxis");
    Firebase.set("data/QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3/lat", 22.552613);
    Firebase.set("data/QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3/lng", 72.923816);
    Firebase.set("data/QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3/name", "Aakash joshi");
    Firebase.set("data/QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3/phone", "9687174843");
    Firebase.set("data/QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3/place_name", "BVM Engineering College");
    Firebase.set("data/QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3/image/image1", "https://media.gettyimages.com/photos/vacant-car-parking-space-picture-id173934806?s=612x612");
    //Firebase.set("Time", t*1000 );
        if (Firebase.failed()) {
      Serial.print("pushing /logs failed:");
      Serial.println(Firebase.error());  
      return;
  
}
  
  }

  else{
    Firebase.set("data/QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3/account", "Owner");
    Firebase.set("data/QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3/car_standing/1/spot", "No");
    Firebase.set("data/QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3/car_standing/1/time", t0);
    Firebase.set("data/QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3/car_standing/2/spot", "No");
    Firebase.set("data/QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3/car_standing/2/time", t0);
    Firebase.set("data/QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3/city", "V V Nagar");
    Firebase.set("data/QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3/cost", "2");
    Firebase.set("data/QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3/email", "amj@gmail.com");
    Firebase.set("data/QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3/gid", "akashjoshi61099@okaxis");
    Firebase.set("data/QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3/lat", 22.552613);
    Firebase.set("data/QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3/lng", 72.923816);
    Firebase.set("data/QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3/name", "Aakash joshi");
    Firebase.set("data/QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3/phone", "9687174843");
    Firebase.set("data/QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3/place_name", "BVM Engineering College");
    Firebase.set("data/QGVsYAYdfiQQ1Fu6vW3CfdBxSlA3/image/image1", "https://media.gettyimages.com/photos/vacant-car-parking-space-picture-id173934806?s=612x612");
    t=0;
    //Firebase.set("Time", t*1000 );
        if (Firebase.failed()) {
      Serial.print("pushing /logs failed:");
      Serial.println(Firebase.error());  
      return;
  
}
  }
//FOR SECOND SENSOR


}
