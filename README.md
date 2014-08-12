# L-Dialogs

* * *

A small library replicating the new dialogs in android L.
* * *

# Usage (Android Studio):

Download the aar here: https://drive.google.com/open?id=0BxdWjlYnGWzgYjJuV2xmekpXbE0&authuser=0

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

You should now be able to access the class CustomDialog from one of your java files.

To create a new CustomDialog simply call

```java
CustomDialog customDialog = new CustomDialog(Context, Title, Content, PositiveText, NegativeText);
or
CustomDialog customDialog = new CustomDialog(Context);

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

This library will be updated often, enjoy!
