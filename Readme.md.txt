 
To Store a PDF as a BLOB in a Database and Retrieve it:

	TABLE cm_pdf_store
		CM_REF_NUM Number(12),
		CM_PDF_NAME VARCHAR2(30),
		CM_PDF_BLOB BLOB
	


1.) First we create the main driver class store_pdf and inside it call the insertion and retrieval fns.


2.) We connect to the database to the driver class using the DriverManager class and JDBC.


3.) Insertion 
	For inserting the PDF into the db PreparedStatement Interface and FileInputStream.
	

4.) Retrieval 
	For retrieving the PDF we use Statement, ResultSet and FileOutputStream to store the blob retrieved from the db at the target file(PDF).
	

    




