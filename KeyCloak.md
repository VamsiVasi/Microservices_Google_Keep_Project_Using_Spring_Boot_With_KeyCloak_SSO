# _KeyCloak (SSO)_
**_How to start and use the KeyCloak in Spring Boot_**

# _Start The KeyCloak Server_

Step 1 : Download [KeyCloak](https://www.keycloak.org/downloads)

Step 2 : Create a directory and unzip the Keycloak

Step 3 : Specify the path of the Keycloak i.e; **_C:\KeyCloak\keycloak-15.0.2\bin_**

Step 4 : Start the Keycloak :
* Open the command line
* Use below command to start the Keycloak
```bash
standalone
```
* By default, Keycloak runs on **_8080_** port and uses **_h2_** database
* We can change the port while starting the Keycloak by using the below command
```bash
standalone.bat -Djboss.http.port=9091
```

# _Creating The Admin Account_

* **_Note : Before Using KeyCloak, you need to create an admin account which you can be used to login to the Keycloak admin console._**

Step 1 : Start the KeyCloak Server

Step 2 : Open ``` http://localhost:<KeyCloak-PortNumber> ``` in your web browser.

Step 3 : The Welcome Page opens, confirming that the server is running.

![image-000](https://user-images.githubusercontent.com/90391735/160666916-1e75a18a-2683-4f38-bb0a-8c409df9d451.png)

Step 4 : Enter a **_Username_** and **_Password_** to create an initial admin user.

# _Logging Into The Admin Console_

* **_Note : After creating the initial admin account, you can login to the admin console. In this console, you can add users and register applications to be secured by
 the KeyCloak_**

Step 1 : Click the **_Administration Console_** link on the Welcome page

Step 2 : Admin Console Login Screen appears :

![image-001](https://user-images.githubusercontent.com/90391735/160669087-7520781f-0bd1-462a-a912-ee60ec33960d.png)

Step 3 : Enter the **_Username_** and **_Password_** you created on the Welcome page to open the **_Admin Console_**.

Step 4 : The initial screen for the admin console appears :

![image-002](https://user-images.githubusercontent.com/90391735/160668278-fea4a516-5d60-4f7f-8bf2-de48cb5b60e4.png)

# _Creating A Realm_

Step 1 : From the **_Master_** menu, click **_Add Realm_**.

![image-003](https://user-images.githubusercontent.com/90391735/160669949-5d963a7a-aaa1-469b-a662-69b01a61fda2.png)

Step 2 : Type name in the **_Name_** field.

![image-004](https://user-images.githubusercontent.com/90391735/160670179-1f39e4ae-08bd-4e54-ada7-30fe106451ce.png)

Step 3 : Click **_Create_**.The main admin console page opens with realm set to ``` <Realm Name> ```.

# _Creating A Client_
  
Step 1 : Click **_Create_** in **_Clients_** Menu.
  
![image-007](https://user-images.githubusercontent.com/90391735/160670637-f9c6fc82-af52-487e-a36c-4809880bf026.jpg)

Step 2 : Enter the **_Client Id_** and Click **_Save_**.

![image-008](https://user-images.githubusercontent.com/90391735/160670724-17cbd405-c9c9-481d-9556-e9c396eb2b81.png)

Step 3 : The Created Client appears, now change the access type from **_public_** to **_confidential_**.
  
![image-011](https://user-images.githubusercontent.com/90391735/160671032-95657e66-7240-4b18-b556-447e05eeeb88.png)

Step 4 : Enter the **_Valid Redirect URIs_** (i.e; URI of the spring boot project) and Click **_Save_**.
  
![image-012](https://user-images.githubusercontent.com/90391735/160671224-dbb9fd21-cd81-4623-b303-008c4f960f81.jpg)

Step 5 : After saving the client, We can find the **_Client Secret_** in **_Credentials_**.

![image-013](https://user-images.githubusercontent.com/90391735/160671542-2683e877-f277-4dca-85fd-34e11ff2ce1b.jpg)

Step 6 : To change the **_Client Secret_**, Click on **_Regenerate Secret_**.

# _Creating A User_

Step 1 : Click **_Add User_** in **_Users_** Menu.

![image-014](https://user-images.githubusercontent.com/90391735/160673023-78dc149e-9426-4db6-ba15-cd2f46853e6b.png)

Step 2 : Enter the details and click on **_Save_**

![image-015](https://user-images.githubusercontent.com/90391735/160673131-ff88e174-9a98-468e-985e-a6c27a725028.png)

Step 3 : The User was created. To provide a password for the user, click on **_Credentials_** option, enter the password and click on **_Set Password_**

![image-018](https://user-images.githubusercontent.com/90391735/160673500-4ffd6bdb-e045-4ce6-bdcd-9b84ae222976.jpg)

Step 4 : While Setting a password, we have a **_Temporary_** option. If the **_Temporary_** option is set to **_ON_**, then the user will asked to set a new password 
as a first time user when he tries to login. If the **_Temporary_** option is set to **_OFF_**, then the user can directly login by using the password what we have 
set in the **_Credentials_**.

![image-019](https://user-images.githubusercontent.com/90391735/160674049-5f53c865-d92d-4902-866c-47a3857fb16d.png)

# _Creating A Role_

Step 1 : Click Add Role in **_Roles_** Menu.

![image-022](https://user-images.githubusercontent.com/90391735/160674458-296e360a-b4a9-46f3-a7a8-516aaf0fb062.png)

Step 2 : Enter the **_Role Name_** and Click on **_Save_**

![image-023](https://user-images.githubusercontent.com/90391735/160674568-d62a79fc-f5c6-45dd-9d2e-140c9141b4e5.png)

# _Assign Roles To Users_

Step 1 : Open the user of what you have created before and click on **_Role Mappings_**

![image-024](https://user-images.githubusercontent.com/90391735/160674754-0df7214f-33a0-4448-8899-0b86c16b7050.jpg)

Step 2 : Here we can see the **_Available Roles_** of what we have created before. To assign a role to the user, select a particular role from **_Available Roles_**
and click on **_Add Selected_**

![image-027](https://user-images.githubusercontent.com/90391735/160675053-d6dd879c-9cfb-426c-a250-31bd78c807d6.png)

Step 3 : Now in the **_Assigned Roles_**, we can see the role of the user of what we have added.

![image-028](https://user-images.githubusercontent.com/90391735/160675262-a1010726-e2fe-4269-ab62-414cc95a9aef.png)

# _Check Users Of A Particular Role_

Step 1 : Open a particular role in **_Roles_** Menu.

![image-029](https://user-images.githubusercontent.com/90391735/160675628-785ed520-c576-4013-bcfd-f4535c276c46.jpg)

Step 2 : Click on **_Users in Role_** and we can see all the users who are assigned for this role.

![image-036](https://user-images.githubusercontent.com/90391735/160675753-7ee237d8-c9ce-443f-bcb9-2e8e65523163.png)

# _Assign Roles To API's In Controller Class_

* To assign a single role for an api, use below configuration
```bash
@PreAuthorize("hasAuthority('<RoleName>')")
```
* To assign multiple roles for an api, use below configuration
```bash
@PreAuthorize("hasAuthority('<RoleName>') or hasAuthority('<RoleName>')")
@PreAuthorize("hasAuthority('<RoleName>') and hasAuthority('<RoleName>')")
```
