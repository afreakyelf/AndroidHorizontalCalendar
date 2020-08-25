<h1 align="center">Android Horizontal Calendar Library</h1>
<p align="center">
A custom Horizontal Calendar with multiple customization options.
<br>
<br>
<img src="https://raw.githubusercontent.com/afreakyelf/HorizontalCalendarLibrary/master/sample.png" width="420" height="800" />
</p>

[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/Apache-2.0) [![](https://jitpack.io/v/afreakyelf/AndroidHorizontalCalendar.svg)](https://jitpack.io/#afreakyelf/AndroidHorizontalCalendar)


## How to integrate into your app?
Integrating the project is simple, all you need to do is follow the below steps

Step 1. Add the JitPack repository to your build file. Add it in your root build.gradle at the end of repositories:

```java
allprojects {
  repositories {
    ...
    maven { url "https://jitpack.io" }
  }
}
```
Step 2. Add the dependency
```java
dependencies {
    implementation 'com.github.afreakyelf:AndroidHorizontalCalendar:<latest_version>'
}
```

## How to use the library?
Okay seems like you have integrated the library in your project but **how do you use it**? Well its really easy just add the following to your xml design to show the calendar

```xml
 <com.example.horizontalcalendar.HorizontalCalender
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:textColor="#863A5E"
        app:selectedColor="#000"
        app:strokeColor="@color/colorAccent"
        app:strokeWidth="4dp"
        app:dayView="true"
        app:showTodayIcon="true"
        app:unSelectedColor="#7485C9"
        />
```
then initialize this in following way : 
```kotlin
horizontal_calendar.initialize(this)     // You will have to implement DateItemClickListener
```

That's pretty much it and you're all wrapped up.

## Attributes
| Attribute | Use |
| ----------| --- |
| app:selectedColor | sets the Color of item you select |
| app:unSelectedColor | sets the Color of item you didn't select |
| app:strokeColor | Color Of the Stroke |
| app:strokeWidth | sets the stroke width |
| app:dayView | sets whether you want days to be shown or not |
| app:showTodayIcon | sets icon for today |



## Donations
**This project needs you!** If you would like to support this project's further development, the creator of this project or the continuous maintenance of this project, **feel free to donate**. Your donation is highly appreciated (and I love food, Tea and beer). Thank you!

**PayPal**

- [**Donate 5 $**](https://www.paypal.me/afreakyelf): Thank's for creating this project, here's a Tea (or some beer) for you!
- [**Donate 10 $**](https://www.paypal.me/afreakyelf): Wow, I am stunned. Let me take you to the movies!
- [**Donate 15 $**](https://www.paypal.me/afreakyelf): I really appreciate your work, let's grab some lunch!
- [**Donate 25 $**](https://www.paypal.me/afreakyelf): That's some awesome stuff you did right there, dinner is on me!
- Or you can also [**choose what you want to donate**](https://www.paypal.me/afreakyelf), all donations are awesome!

## Author
Maintained by [Rajat Mittal](https://www.github.com/afreakyelf)

## License
```
MIT License

Copyright (c) 2019 Rajat MIttal

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
