# GroupAssignment1
Group Assignment 1

Author: Daniel

All the classes go in the Project Folder, I have already created each class in java to get us going.
The FileOperations class is already completed, if there are any problems with it or it needs more methods feel free to fix them or contact me.

Edit this file for discussions on the code or questions for others:

melnicarol238@gmail.com 612-600-3046

danieltrksak@gmail.com 612-226-9821

Nebiyu.Ashagre@gmail.com 651-274-7262

poderman64@yahoo.com 651-235-2575

I'll be starting testing tonight.

Just so you know, I did a round of testing and updated the classes. The only thing that doesn't work (as far as I know) is importing a json file. The problem was related to what the json was imported as, since it isn't imported as a list of objects, its imported as a single object.


I've shared the class diagram with all of you if you wanted to make changes and we can post it.


As far as i could tell this would be the best way to read in the json file into a string so that the Json Handler can use it. I dont know if it will read in the whole json file into the string at once, i didnt have time to check with the code. 
   BufferedReader br = new BufferedReader(
     new FileReader("E:file.json"));

Guess what guys? Re-download the classes and try out the program. Tell me what you think. :>

So i created a sequence diagram... the only thing im not sure about it when adding a shipment the warehouse handler gets passed a shipment object, im not to sure how to show that the addShipment method creates a shipment.

The WarehouseHandler gets it from JsonHandler or InputHandler.

https://www.lucidchart.com/invitations/accept/3d04bd00-4774-4c47-b699-bdafccfd775c

sequence diagram:
https://www.lucidchart.com/invitations/accept/624efcfe-a33d-4f89-91c2-63d050d8fb3b

/------/

