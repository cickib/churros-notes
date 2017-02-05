ChurrosNotes app\*
============
![alt tag](/Churros/app/src/main/res/drawable/screenshot.png)

A *very* lightweight app to store notes. Notes can be sorted by date or ABC, ascending or descending. Delete them if they're no longer needed, one by one or at once.

This annoyingly user-friendly and stupid-proof app displays infos as `Toast` messages and asks for confirmation on everything. Copying a note to the device's clipboard is enabled.

If it won't compile, check `build.gradle` and update [according to this](https://developer.android.com/guide/platform/j8-jack.html).

The app can be used on (probably) any device that uses `Android 4.3+ (Jelly Bean, API 18)`.

![alt tag](http://www.ninajohansson.se/wp-content/uploads/2011/04/Barcelona_9.jpg)
<br />
art by [Nina Johansson](http://www.ninajohansson.se/)
<br />

My project idea
============

I'm going to develop a basic android app that receives user input, saves them to a db, and displays them back to the user. Deleting is also possible. So instead of taking notes on multiple pieces of paper and then forgetting about them, or not having a paper/pen when needed, my *extraordinary* app with extensive sorting features (alphabetic/time of creation) is there to do the job.

*<p align="center">
:notebook: -> :sparkles: magic :sparkles: -> :iphone:
</p>*

The db should have a `notes` table, consisting of 3 columns: a `primary_key` one, two `TEXT` type ones, `text`, and `date`, respectively.

The layout is gonna be as basic as it gets: the input field and the displayed data will be on the same page, without much navigation. Sorting buttons on top.

* If, for some reason, I'll finish with these quicker than expected, I'll implement a basic filtering feature.


To-do
============
* getting familiar with basic concepts in android development (`View`, `Intent`, `Activity`, etc.)
* putting together a *Hello Word*  by myself, starting the project from scratch
* getting familiar with SQLite, focusing on it in relation to android
* creating the structure of the app (folders, etc.)
* creating the input field and saving the data
* retrieving the data and displaying it
* implementing the sorting:
  * `Button` for alphabetic order (ascending/descending)
  * `Button` for time of creation (ascending/descending)
* deleting from db when no longer needed
* if I have too much time
  * implementing the filtering: input field that searches the db with some basic `regex`
  * after or before the latter, applying some minimal design


Technology stack
============
Java, Android 4.3+ (Jelly Bean, API 18), SQLite

\* because it's OK to call `Android` related stuff a snack :cookie:
<br />
