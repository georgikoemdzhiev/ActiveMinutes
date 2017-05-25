# ActiveMinutes
This project is developed as part of my BSc Honours Degree in Computing. It is detailed described in my Thesis document which can be found <a href="https://github.com/georgikoemdzhiev/BSC_Thesis">here</a>

ActiveMinutes is an Android application that allows the users to monitor their active and inactive time. They can set goals, the application will monitor their activity levels and send appropriate notifications. 
The application utilises concepts such as <strong>Dependency Injection</strong> by utilising Dagger 2 framework. The app also implements the <strong>MVP</strong> developing pattern to split the code into logical layers and make the application easy to test.
The application only uses one sensor - the build in accelerometer sensor to detect 4 states of the user: runnung, cyclking, walking and static. The custom classifier supplied with the application allows the app to work completely <strong>offline</strong>  without the need of network calls (compared to GoogleActivityRecognitionApi).
