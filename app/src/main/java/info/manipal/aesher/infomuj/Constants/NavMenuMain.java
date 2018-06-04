package info.manipal.aesher.infomuj.Constants;

import android.content.Context;

import java.util.List;

import info.manipal.aesher.infomuj.Adapters.ClubProvider;
import info.manipal.aesher.infomuj.Adapters.ContactProvider;
import info.manipal.aesher.infomuj.R;

public class NavMenuMain {

    public void engineering(List<ClubProvider> provider) {

        ClubProvider p1 = new ClubProvider("C", "CSE", "NaN", false, 6);
        provider.add(p1);
        p1 = new ClubProvider("I", "IT", "NaN", false, 5);
        provider.add(p1);
        p1 = new ClubProvider("C", "CCE", "NaN", false, 10);
        provider.add(p1);
        p1 = new ClubProvider("E", "Electrical", "NaN", false, 1);
        provider.add(p1);
        p1 = new ClubProvider("E", "ECE", "NaN", false, 8);
        provider.add(p1);
        p1 = new ClubProvider("M", "Mechanical", "NaN", false, 7);
        provider.add(p1);
        p1 = new ClubProvider("A", "Automobile", "NaN", false, 2);
        provider.add(p1);
        p1 = new ClubProvider("M", "Mechatronics", "NaN", false, 9);
        provider.add(p1);
        p1 = new ClubProvider("C", "Chemical", "NaN", false, 3);
        provider.add(p1);
        p1 = new ClubProvider("C", "Civil", "NaN", false, 4);
        provider.add(p1);

    }


    public void clubs(List<ClubProvider> provider) {
        ClubProvider p1 = new ClubProvider("A", "ACM MUJ", "NaN", false, 29);
        provider.add(p1);
        p1 = new ClubProvider("A", "Aperture", "NaN", false, 25);
        provider.add(p1);
        p1 = new ClubProvider("C", "Cinefilia", "NaN", false, 23);
        provider.add(p1);
        p1 = new ClubProvider("I", "IAESTE MUJ", "NaN", false, 22);
        provider.add(p1);
        p1 = new ClubProvider("I", "IEEE SB MUJ", "NaN", false, 27);
        provider.add(p1);
        p1 = new ClubProvider("L", "Litmus", "NaN", false, 26);
        provider.add(p1);
        p1 = new ClubProvider("R", "RPM", "NaN", false, 28);
        provider.add(p1);
        p1 = new ClubProvider("T", "TMC", "NaN", false, 24);
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

    public void college(List<ClubProvider> providerCollege) {
        ClubProvider p1 = new ClubProvider("A", "About MUJ", "NaN", false, 14);
        providerCollege.add(p1);
        p1 = new ClubProvider("A", "Admission", "NaN", false, 15);
        providerCollege.add(p1);
        p1 = new ClubProvider("C", "Campus Facilities", "NaN", false, 16);
        providerCollege.add(p1);
        p1 = new ClubProvider("C", "College Fee", "NaN", false, 11);
        providerCollege.add(p1);
        p1 = new ClubProvider("D", "Directors", "NaN", false, 21);
        providerCollege.add(p1);
        p1 = new ClubProvider("H", "Hostel Fee", "NaN", false, 13);
        providerCollege.add(p1);
        p1 = new ClubProvider("M", "Medical Facilities", "NaN", false, 19);
        providerCollege.add(p1);
        p1 = new ClubProvider("P", "Placement", "NaN", false, 20);
        providerCollege.add(p1);
        p1 = new ClubProvider("R", "Room Facilities", "NaN", false, 17);
        providerCollege.add(p1);
        p1 = new ClubProvider("S", "Scholarships", "NaN", false, 30);
        providerCollege.add(p1);
        p1 = new ClubProvider("S", "Sports Facilities", "NaN", false, 18);
        providerCollege.add(p1);
    }
}



