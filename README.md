Hello everyone. My name is Kostya.

This is my first android project so please don't laugh outloudly.
Project was build and compiled with next SKD's:
	Minimum required : SDK API 10;
	Target : SDK API 10;
	Compile with : API 19;
	I implemented one screen with rss links and other one with it's correspondent 
page content.
	I have problems with screen orientation, when I rotate screen to LandScape everything works, 
but when I return it to portrait orientation screen content is not rotating.

	Also I read that it's preferably not to use WebView but show pictures using android widgets.
If it's critical I can write parser that will parse page content, extract pictures from it and 
after build page manually using WebView for text and Image Bitmap for pictures.

	Links of the first screen stored in SQLite and when you return from second screen data retrieved 
from DB. Meanwhile you can refresh your data pressing button Update.

	Page content on the second screen also stored in SQLite DB. So if you visited page first, on second
time it will loaded not from server but DB. Also you can clear page cache pressing button
"Clear page cache". This action will remove page from DB and next time page will be downloaded from 
remote sever. There is another button - "Clear all cache" if you want to remove all pages cached in DB.

P.S. First time I downloaded to repository a lot of files, I think most of them are not necessary. In
near future I will found out which one of them actually needed. All other will be included to .gitignore.
Also I will be appreciated if write some remarks. I'm waiting for response what's wrong and what should I add.
Many thanks in advance.  