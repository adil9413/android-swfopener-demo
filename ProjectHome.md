how to call play swf file on swfopener

you can found the app from

http://play.google.com/store/apps/details?id=com.ryan.swf.opener

Developer:
> if you a developer and you maybe want to show swf to your customer on this app.
> there have two way:
> API 1:
> > Intent intent = new Intent();
> > > intent.setClassName("com.ryan.swf.opener", "com.ryan.swf.opener.MainActivity");
> > > intent.putExtra("play\_swf\_list", "t=#title#|lp=#local\_swf\_path#|np=#net\_url\_path#");
> > > startActivity(intent);

> > NOTE: please replace #title# to your swf title and #local\_swf\_path# to your swf local path .

> API 2:
> > Intent intent = new Intent();
> > > intent.setAction(Intent.ACTION\_VIEW);
> > > intent.setClassName("com.ryan.swf.opener", "com.ryan.swf.opener.MainActivity");
> > > intent.setData(Uri.fromFile(file));
> > > startActivity(intent);

> Demo：
> > http://code.google.com/p/android-swfopener-demo/
