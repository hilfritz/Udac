http://api.football-data.org/docs/latest/index.html


A Soccerseason represents a particular season of a football league, so for example the running competition of the Premiere League is a Soccerseason.

A Soccerseason belongs to a specific League which is defined by a two or three-letter-code. (click here for a complete listing) and consists of Matchdays that hold a number of scheduled games, named Fixtures. A certain number of Teams participate in a Soccerseason.
Note that I omitted to implement Matchday as a hierarchical element by purpose. It’s just an attribute of Fixture because I think this is more intuitive and separation can be achieved by using a Filter. In tournamenets like the Champions League a matchday represents the round of the last 16, the semi-final as well, so be sure to adapt your frontend accordingly ;)



Loaders
http://www.androiddesignpatterns.com/2012/07/understanding-loadermanager.html
https://stackoverflow.com/questions/11150527/how-does-cursorloader-with-loadermanager-know-to-send-the-cursor-to-a-cursoradap/11158889#11158889
http://code.tutsplus.com/tutorials/android-fundamentals-properly-loading-data--mobile-5673
http://www.recursiverobot.com/post/60331340133/very-simple-example-of-a-loader-and-loadermanager
http://www.grokkingandroid.com/using-loaders-in-android/


DATABASE ACCESS WITH SQLLITE AND CURSOR LOADERS
https://stackoverflow.com/questions/18326954/how-to-read-an-sqlite-db-in-android-with-a-cursorloader
https://stackoverflow.com/questions/9912399/using-cursorloader-to-query-sqlite-db-and-populate-autocompletetextview


ScoresDBHelper ->(DATE_COL)-> Databasecontract


WIDGETS
https://laaptu.wordpress.com/2013/07/19/android-app-widget-with-listview/
https://github.com/laaptu/appwidget-listview/tree/appwidget-listview1
https://github.com/laaptu/twitterwidget
https://shebella1014.wordpress.com/2015/04/21/android-app-widget-with-listview/
https://laaptu.wordpress.com/2013/07/24/populate-appwidget-listview-with-remote-datadata-from-web/
https://stackoverflow.com/questions/7940280/android-get-widget-data-from-database
30 min update period- http://stackoverflow.com/questions/9744198/onupdate-never-called-android-widget
explicitly call onUpdate on AppWidgetProvider
http://stackoverflow.com/questions/6437341/how-to-call-onupdate-in-appwidgetprovider
http://stackoverflow.com/questions/3455123/programmatically-update-widget-from-activity-service-receiver
Open activity from widget click
https://stackoverflow.com/questions/1937236/launching-activity-from-widget
https://stackoverflow.com/questions/2471875/processing-more-than-one-button-click-at-android-widget

SQL
https://stackoverflow.com/questions/10600670/sqlitedatabase-query-method
http://www.tutorialspoint.com/android/android_sqlite_database.htm
http://developer.android.com/guide/topics/providers/content-provider-basics.html



RECEIVERS
http://www.techotopia.com/index.php/Android_Broadcast_Intents_and_Broadcast_Receivers

Layout Mirroring
http://stackoverflow.com/questions/17512504/change-action-bar-direction-to-right-to-left/17683045#17683045
http://stackoverflow.com/questions/20593628/how-to-specify-rtl-specific-drawables