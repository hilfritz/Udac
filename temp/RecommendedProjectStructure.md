# Recommended project structure for Android projects.

This is one of the suggested directory structure for Android project

### /src/my.company.package/
```sh
 /application
```
- instantiate here the application class, singleton objects

```sh
 /bus
 ```
- This contains your fragments, adapters, activities like

```sh
/utils
```
```sh
/ui
```
-  This contains your fragments, adapters, activities like
- sample:
>/login/LoginActivity.java

>/login/LoginFragment.java

>/login/LoginAdapter.java

>/BaseActivity.java

>/BaseFragment.java

```sh     
/api
```
-  models, client class, database, pojo, rest
```sh
/delegate
```
-   Calculations, 
