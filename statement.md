# statement.md

## 📌 Problem Statement
Creating student identity cards manually using design tools is time-consuming, repetitive, and error-prone.  
Small mistakes like incorrect names, photos, or formatting inconsistencies require redoing the entire card.  
There is a need for a simple software tool that can automatically generate standardized and professional ID cards based on user-entered details.

---

## 🎯 Scope of the Project
The scope of this project includes:
- A desktop Java application with a clean GUI
- Input fields for student details (name, ID, DOB, course, blood group, address)
- Option to upload a student photo
- Automatic generation of **front** and **back** ID card layouts
- Live preview of both sides
- Saving the generated cards as PNG image files
- Customization of college name, address, and theme using `.env`

This project does **not** include database integration, networking, admin login, or large-scale card management.

---

## 👥 Target Users
- School and college administrators  
- Institute ID card creation departments  
- Students making academic Java projects  
- Anyone needing a simple, offline ID card generator  

---

## 🚀 High-Level Features
1. **Student Information Input**  
   Fields for name, student ID, DOB, blood group, course, and address.

2. **Photo Upload**  
   Allows choosing a JPG/PNG file.

3. **Front & Back Card Rendering**  
   Uses Java2D to generate both sides of the ID card.

4. **Theme & College Customization**  
   Editable through `.env` (college name, colors, website, etc.)

5. **Save as PNG**  
   Button to export both card sides as image files.

---

