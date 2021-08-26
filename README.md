
![banner](https://user-images.githubusercontent.com/88869576/130990605-514f741e-8113-465d-bba5-64700b490d3e.png)
  
## [SaFoDel](https://m-posd.github.io/)
Educating Food Delivery Bike Riders in Victoria

<img src="https://user-images.githubusercontent.com/88869576/130999668-f6d115d8-a5d7-4e58-bb18-cd1047a8add2.gif">


## Features✨
- E-Bike Delivery information share (Find information on using e-bikes for food delivery)
- Safety Gears information share (Find out the cycling gear you need to deliver safe)
- Ride Safer tips share (Find out how to ride safely while delivering food)
- A map to see accident location base on LGA area
- Locate the current location
- A good Logo
- A good UI
 
## Download

Download `.apk` file on our [Website](https://m-posd.github.io/) or on the [Release](https://github.com/M-POSD/SaFoDel/releases) page

## Android development

- Written in [Kotlin](https://kotlinlang.org/) 
- Using [Mapbox](https://www.mapbox.com/) developed map functions
- Using python built a server in [Heroku](https://www.heroku.com)

## Building
### Open the Project in [Android Studio](https://developer.android.com/studio)

```
git clone https://github.com/M-POSD/SaFoDel.git
```
Open the `SaFoDel/` directory in Android Studio.

### Get Started
```shell
./gradlew build
```
### Tips
- Require the latest Android Studio Arctic Fox release to be able to build the app
- Update the Kotlin to latest version.
- Emulator with the latest api 30 (by clicking on the AVD Manager on the navigation bar).

## Mapbox tokens

Follow Mapbox's [docs](https://docs.mapbox.com/help/getting-started/access-tokens/) to create your tokens.

- Edit "mapbox_access_token" in `res/values/strings.xml`.
- Edit "MAPBOX_DOWNLOADS_TOKEN" in `/gradle.properties`




