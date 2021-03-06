# JavaPong
### A pong implementation inspired by the classic Atari arcade game. Built in Java using the JavaFX API.

I decided to do my own implementation of Pong so I could take an opportunity to get comfortable with the JavaFX API having only used Swing in the past. 

---

### UI:
![ui](src/main/resources/img/pong.gif)

---

### States:
1. Main Menu
2. Game play
3. Max score reached
4. Press 'q' to go to back to Main Menu or 'r' to replay.

---

### Implementation

#### Main(): extends javafx.application.Application

#### Game():
- CheckForScore(Paddle paddle) : boolean
- cpuPaddleControl(Paddle paddle)
- endMatchMessage(String message)
- resetBall()
- startMatch()
- xCollision() : boolean
- yCollision() : boolean

#### Ball(): extends javafx.scene.shape.Circle
- incrementXVelocity()
- incrementYVelocity()
- reverseXVelocity()
- reverseYVelocity()

#### Paddle(): extends javafx.scene.shape.Rectangle


