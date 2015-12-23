Recommended Project Structure
===================


This is one of the suggested directory structure for Android project

/src/my.company.package/

-/application
:   instantiate here the application class, singleton objects

-/bus
:   This contains your fragments, adapters, activities like

-/utils
-/ui
:   This contains your fragments, adapters, activities like
:   sample:
/login/LoginActivity.java
/login/LoginFragment.java
/login/LoginAdapter.java
/BaseActivity.java
/BaseFragment.java
         
-/api
:   models, client class
/database
/pojo
/rest

-/delegate
:   Core codes