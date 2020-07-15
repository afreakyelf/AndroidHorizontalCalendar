<h1 align="center">HorizontalCalendarLibrary</h1>
<p align="center">
A custom Horizontal Calendar with multiple customization options.
<br>
<br>
<img src="https://raw.githubusercontent.com/afreakyelf/HorizontalCalendarLibrary/master/WhatsApp%20Image%202019-09-23%20at%2021.28.42.jpeg" width="420" height="260" />
</p>

## How to integrate into your app?
Integrating the project is simple a refined all you need to do is follow the below steps

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
    implementation 'com.github.afreakyelf:HorizontalCalendarLibrary:<latest_version>'
}
```

## How to use the library?
Okay seems like you integrated the library in your project but **how do you use it**? Well its really easy just add the following to your xml design to show the calendar

```xml
.....
 <com.example.horizontalcalendar.HorizontalCalender
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:textColor="#863A5E"
        app:selectedColor="#000"
        app:strokeColor="@color/colorAccent"
        app:strokeWidth="4"
        app:calenderIcon="@drawable/cal" //Mandatory
        app:dayView="true"
        app:showTodayIcon="true"
        app:unSelectedColor="#7485C9"
        />
.....
```

That's pretty much it and your all wrapped up.

## Attributes
| Attribute | Use |
| ----------| --- |
| app:selectedColor | sets the Color of item you select |
| app:unSelectedColor | sets the Color of item you didn't select |
| app:strokeColor | Color Of the Stroke |
| app:strokeWidth | sets the stroke width |
| app:calenderIcon | sets the icon for your calendar |
| app:dayView | sets whether you want days to be shown or not |
| app:showTodayIcon | sets icon for today |


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
