# invent-tories
Android Studio inventory app implementing ZXing barcode/QR code scanner and using a RESTful API service, loading information from a json file (from online) into a Room Database. 

Developed with partner, Bilha Ghedeon, for Mobile Device Application Principles course. 

Video showing app functionality: https://youtu.be/w8R8lJ0NwZw

Project uses ZXing Library and Barcode LookUp RESTful API. Further details on these topics in the implementation pdf.

How app works...

Overview : 
- User can scan item
- Information on item is given
- User can add additional information about item
- User can then add the item to their inventory if they choose
- User can delete/update items from their inventory
- Daily Report shows item and item quantity 

QR Code Functionality - using json files :
1. Created json files (example json files provided) for individual products
2. Uploaded them to our website
3. Made QR codes using QR Code Generator: http://goqr.me/
4. When code is scanned, app recognizes this is a QR code and fetches the json from the website. Parses information into Item object and inserts object into user’s database.

Barcode Functionality – using the Barcode Lookup RESTful API Service :
1. When code is scanned, app recognizes this is a barcode and fetches the json offered by the Barcode Lookup API service. Parses information into Item object and inserts object into user’s database. 
2. Parsed for particular information from the json offered by API service to match the json files manually written by our team for the QR code items – due to simplicity and time constraints. 
