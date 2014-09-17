# L-Dialogs

* * *

A small library replicating the new dialogs in android L.

!["Screenshot 1"](https://github.com/lewisjdeane/L-Dialogs/raw/master/screenshots/banner.jpg)

* * *

# Set Up (Android Studio):

Download the aar here: https://www.dropbox.com/s/276bhapr2g50cak/ldialogs.aar?dl=0

Maven central support will be coming soon.

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

##Normal Dialogs

You should now be able to access the class CustomDialog from one of your java files.

To create a new CustomDialog we need to use a builder as so:

```java
// Create the builder with required paramaters - Context, Title, Positive Text
CustomDialog.Builder builder = new CustomDialog.Builder(Context context, String title, String positiveText);

// Now we can any of the following methods.
builder.content(String content);
builder.negativeText(String negativeText);
builder.darkTheme(boolean isDark);
builder.typeface(Typeface typeface);
builder.titleTextSize(int size);
builder.contentTextSize(int size);
builder.buttonTextSize(int size);
builder.titleAlignment(Alignment alignment); // Use either Alignment.LEFT, Alignment.CENTER or Alignment.RIGHT
builder.titleColor(String hex); // int res, or int colorRes parameter versions available as well.
builder.contentColor(String hex); // int res, or int colorRes parameter versions available as well.
builder.positiveColor(String hex); // int res, or int colorRes parameter versions available as well.
builder.negativeColor(String hex); // int res, or int colorRes parameter versions available as well.
builder.positiveBackground(Drawable drawable); // int res parameter version also available.
builder.rightToLeft(boolean rightToLeft); // Enables right to left positioning for languages that may require so.

// Now we can build the dialog.
CustomDialog customDialog = builder.build();

// Show the dialog.
customDialog.show();
```

To handle the button clicks you can use the following code:

```java
customDialog.setClickListener(new CustomDialog.ClickListener() {
            @Override
            public void onConfirmClick() {

            }

            @Override
            public void onCancelClick() {

            }
        });
```

If you want to set a custom view in the dialog you can use the following method.

```java
customDialog.setCustomView(View customView);
```

Then do what you need to do with the custom views content in onConfirmClick or onCancelClick.


##List Dialogs

To use the CustomListDialog we need to use a builder again, this is done as follows:

```java
// Create list dialog with required parameters - context, title, and our array of items to fill the list.
CustomListDialog.Builder builder = new CustomListDialog.Builder(Context context, String title, String[] items);

// Now again we can use some extra methods on the builder to customise it more.
builder.darkTheme(boolean isDark);
builder.typeface(Typeface typeface);
builder.titleAlignment(Alignment alignment); // Use either Alignment.LEFT, Alignment.CENTER or Alignment.RIGHT
builder.itemAlignment(Alignment alignment); // Use either Alignment.LEFT, Alignment.CENTER or Alignment.RIGHT
builder.titleColor(String hex); // int res, or int colorRes parameter versions available as well.
builder.itemColor(String hex); // int res, or int colorRes parameter versions available as well.
builder.titleTextSize(int size);
builder.itemTextSize(int size);
builder.rightToLeft(boolean rightToLeft); // Enables right to left positioning for languages that may require so.

// Now we can build our dialog.
CustomListDialog customListDialog = builder.build();

// Finally we can show it.
customListDialog.show();
```

In order to recieve the click events from the dialog, simply use the following method on your customListDialog:
```java
customListDialog.setListClickListener(new CustomListDialog.ListClickListener() {
            @Override
            public void onListItemSelected(int i, String[] strings, String s) {
                // i is the position clicked.
                // strings is the array of items in the list.
                // s is the item selected.
            }
        });
``` 

To add a listview selector use the following code:
```java
StateListDrawable selector = new StateListDrawable();
selector.addState(new int[]{android.R.attr.state_pressed}, new ColorDrawable(R.color.color1));
selector.addState(new int[]{-android.R.attr.state_pressed}, new ColorDrawable(R.color.color2));

// The important part:
customListDialog.getListView().setSelector(selector);
```

* * *

This library will be updated often, enjoy!
