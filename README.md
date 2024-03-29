# ElementalCommunityWeb
Web Edition of [Elemental Community](https://github.com/AntonioNoack/ElementalCommunity) (frontend only)

## Play Game
To play the game, visit [its website](https://elemental.phychi.com).

## Technical Overview
This project is using a helper script in the main project to copy most business-logic files over.
The Android API, or what we need, has been replaced with custom implementations, plus some slight adjustments to our files.

The UI is using the same components as in Android, drawn onto an HTMLCanvas.
Their classes have been simplified from the originals to only what we need.
They are copied from XML format to Kotlin code using the script in the main project.

Any inflated copies are just clones of these base UIs.