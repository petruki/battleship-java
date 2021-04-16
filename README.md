![Maven CI/CD](https://github.com/petruki/battleship-java/workflows/Maven%20CI/CD/badge.svg)

### Battleship Java

Battleship Java is a simple Java Swing based game app.

 - Sink ships give you extra point
 - Miss 3 times give you the radar power up for 5 seconds

![Battleship UI](https://raw.githubusercontent.com/petruki/battleship-java/master/docs/main.jpg)


#### Features

- Know how much time has left
- Scoreboard
- Interactive board. Click and hit fire or Double-click to fire!
- Custom settings
- Radar power up!
- Online Multiplayer - Requires running: [battlership-broker server](https://github.com/petruki/battleship-broker)


#### Import to IDE

Import the project as Maven Project.
* Requires Java 11 or superior

#### Compiling using Maven

**Compile and test**
```
mvn clean install
```

**Generate executable**
```
mvn clean compile assembly:single
```

#### Contribution

1. Test.
2. Build.
3. Test again!
4. Submit your PR.

![Test](https://raw.githubusercontent.com/petruki/battleship-java/master/docs/test.jpg)