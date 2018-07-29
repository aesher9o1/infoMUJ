package info.manipal.aesher.infomuj.Constants;

import android.content.Context;

import java.util.List;

import info.manipal.aesher.infomuj.Adapters.ClubProvider;
import info.manipal.aesher.infomuj.Adapters.ContactProvider;
import info.manipal.aesher.infomuj.R;

public class NavMenuMain {

    public void engineering(List<ClubProvider> provider) {
        ClubProvider p1;
        p1 = new ClubProvider("C", "CSE", "NaN", 6);
        provider.add(p1);
        p1 = new ClubProvider("I", "IT", "NaN", 5);
        provider.add(p1);
        p1 = new ClubProvider("C", "CCE", "NaN", 10);
        provider.add(p1);
        p1 = new ClubProvider("E", "Electrical", "NaN", 1);
        provider.add(p1);
        p1 = new ClubProvider("E", "ECE", "NaN", 8);
        provider.add(p1);
        p1 = new ClubProvider("M", "Mechanical", "NaN", 7);
        provider.add(p1);
        p1 = new ClubProvider("A", "Automobile", "NaN", 2);
        provider.add(p1);
        p1 = new ClubProvider("M", "Mechatronics", "NaN", 9);
        provider.add(p1);
        p1 = new ClubProvider("C", "Chemical", "NaN", 3);
        provider.add(p1);
        p1 = new ClubProvider("C", "Civil", "NaN", 4);
        provider.add(p1);

    }

    public void college(List<ClubProvider> providerCollege) {
        ClubProvider p1 = new ClubProvider("A", "About MUJ", "NaN", 14);
        providerCollege.add(p1);
        p1 = new ClubProvider("A", "Admission", "NaN", 15);
        providerCollege.add(p1);
        p1 = new ClubProvider("C", "Campus Facilities", "NaN", 16);
        providerCollege.add(p1);
        p1 = new ClubProvider("C", "College Fee", "NaN", 11);
        providerCollege.add(p1);
        p1 = new ClubProvider("D", "Directors", "NaN", 21);
        providerCollege.add(p1);
        p1 = new ClubProvider("H", "Hostel Fee", "NaN", 13);
        providerCollege.add(p1);
        p1 = new ClubProvider("M", "Medical Facilities", "NaN", 19);
        providerCollege.add(p1);
        p1 = new ClubProvider("P", "Placement", "NaN", 20);
        providerCollege.add(p1);
        p1 = new ClubProvider("R", "Room Facilities", "NaN", 17);
        providerCollege.add(p1);
        p1 = new ClubProvider("S", "Scholarships", "NaN", 30);
        providerCollege.add(p1);
        p1 = new ClubProvider("S", "Sports Facilities", "NaN", 18);
        providerCollege.add(p1);
    }


    public void clubs(List<ClubProvider> provider) {
        ClubProvider p1 = new ClubProvider("A", "ACM", "NaN", 29);
        provider.add(p1);
        p1 = new ClubProvider("A", "Aperture", "NaN", 25);
        provider.add(p1);
        p1 = new ClubProvider("C", "Cinefilia", "NaN", 23);
        provider.add(p1);
        p1 = new ClubProvider("C", "CSI", "NaN", 34);
        provider.add(p1);
        p1 = new ClubProvider("E", "E-Cell", "NaN", 33);
        provider.add(p1);
        p1 = new ClubProvider("I", "IAESTE", "NaN", 22);
        provider.add(p1);
        p1 = new ClubProvider("I", "IEEE", "NaN", 27);
        provider.add(p1);
        p1 = new ClubProvider("L", "Litmus", "NaN", 26);
        provider.add(p1);
        p1 = new ClubProvider("R", "RPM", "NaN", 28);
        provider.add(p1);
        p1 = new ClubProvider("T", "TMC", "NaN", 24);
        provider.add(p1);

    }

    public void policies(List<ClubProvider> provider) {

        ClubProvider p1 = new ClubProvider("A", "Academic Rules & Regulations", "NaN", 35);
        provider.add(p1);
        p1 = new ClubProvider("D", "Disclaimer", "NaN", 36);
        provider.add(p1);
        p1 = new ClubProvider("F", "Fee Refund Rules", "NaN", 37);
        provider.add(p1);
        p1 = new ClubProvider("H", "Hostel Rules & Regulations", "NaN", 38);
        provider.add(p1);
    }


    public void contact(List<ContactProvider> provider, Context context) {
        ContactProvider p1 = new ContactProvider("Administration Blocks", "admin", R.drawable.ic_admin);
        provider.add(p1);
        p1 = new ContactProvider("Finance Department", "finance", R.drawable.ic_finance);
        provider.add(p1);
        p1 = new ContactProvider("Food Court", "food", R.drawable.ic_food);
        provider.add(p1);
        p1 = new ContactProvider("Hostel Gym", "gym", R.drawable.ic_gym);
        provider.add(p1);
        p1 = new ContactProvider("Student Library", "library", R.drawable.ic_library);
        provider.add(p1);
    }


}



