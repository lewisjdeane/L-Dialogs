# L-Dialogs

* * *

A small library replicating the new dialogs in android L.

!["Screenshot 1"](https://github.com/lewisjdeane/L-Dialogs/raw/master/app/src/main/res/screenshots/screen3.png)

* * *

# Set Up (Android Studio):

Download the aar here: https://www.dropbox.com/s/uqey0k7m3g45ky4/app.aar

You can rename the aar and then place it in the libs directory of your project.

Go into your build.gradle and add the following:
```java

dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])
    compile 'uk.me.lewisdeane.ldialogs:RENAMED_FILE_NAME_HERE@aar'
}

repositories{
    flatDir{
        dirs 'libs'
    }
}

```

# Usage

= Normal Dialogs

You should now be able to access the class CustomDialog from one of your java files.

To create a new CustomDialog simply call

```java
CustomDialog customDialog = new CustomDialog(Context, Title, Content, PositiveText, NegativeText);
or
CustomDialog customDialog = new CustomDialog(Context);
or
CustomDialog customDialog = new CustomDialog(Context, Title, Content, PositiveText);

and then 

customDialog.show();
```

Method calls are also available

```java
customDialog.setConfirmColour(String hex);
customDialog.setTitle(String title);
customDialog.setContent(String content);
customDialog.setConfirm(String confirm);
customDialog.setCancel(String cancel);

```

In order to set the click listeners for the two buttons the activity that created the CustomDialog must implement CustomDialog.ClickListener, and then use the methods

```java
public void onConfirmClick(){
...
}

public void onCancelClick(){
...
}

```

= List Dialogs

You can access the class CustomListDialog in a similar fashion to previously.

To create a new list dialog simply call
```java
CustomListDialog customListDialog = new CustomListDialog(Context context);
or
CustomListDialog customListDialog = new CustomListDialog(Context context,String title, ArrayList<String> items);

and then

customListDialog.show();
```

Method calls are just as easy:
```java
customListDialog.setTitle(String title);
customListDialog.setItems(ArrayList<String> items);
customListDialog.setItems(String[] items);
customListDialog.setCenterAligned(boolean isCentered);
customListDialog.setTitleColour(String hexColour);
customListDialog.setListItemColour(String hexColour);
```


In order to recieve the click events from the dialog, the activity that created the dialog must implement CustomListDialog.ListClickListener and then use the method:

```java
public void onListItemSelected(int position, ArrayList<String> items, String item){
    // position is the position in list of selected item.
    // items is the list that is filling the dialog.
    // item is the selected item therefore item = items.get(position);
    
    // Do stuff here based on item click.
}

```

* * *

This library will be updated often, enjoy!
