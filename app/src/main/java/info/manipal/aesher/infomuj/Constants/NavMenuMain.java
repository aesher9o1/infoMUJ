package info.manipal.aesher.infomuj.Constants;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import java.util.List;

import info.manipal.aesher.infomuj.Adapters.ClubProvider;
import info.manipal.aesher.infomuj.Adapters.ContactProvider;
import info.manipal.aesher.infomuj.R;

public class NavMenuMain {

    public void engineering(List<ClubProvider> provider){

        ClubProvider p1 = new ClubProvider("C","CSE","NaN",false,6);
        provider.add(p1);
        p1 = new ClubProvider("I","IT","NaN",false,5);
        provider.add(p1);
        p1 = new ClubProvider("C","CCE","NaN",false,10);
        provider.add(p1);
        p1 = new ClubProvider("E","Electrical","NaN",false,1);
        provider.add(p1);
        p1 = new ClubProvider("E","ECE","NaN",false,8);
        provider.add(p1);
        p1 = new ClubProvider("M","Mechanical","NaN",false,7);
        provider.add(p1);
        p1 = new ClubProvider("A","Automobile","NaN",false,2);
        provider.add(p1);
        p1 = new ClubProvider("M","Mechatronics","NaN",false,9);
        provider.add(p1);
        p1 = new ClubProvider("C","Chemical","NaN",false,3);
        provider.add(p1);
        p1 = new ClubProvider("C","Civil","NaN",false,4);
        provider.add(p1);

    }




    public void clubs(List<ClubProvider> provider){
        ClubProvider p1 = new ClubProvider("I","IEEE SB MUJ","NaN",false,-1);
        provider.add(p1);
        p1 = new ClubProvider("A","ACM MUJ","NaN",false,-1);
        provider.add(p1);
        p1 = new ClubProvider("I","IAESTE MUJ","NaN",false,-1);
        provider.add(p1);
    }

    public void contact(List<ContactProvider> provider, Context context){
        ContactProvider p1 = new ContactProvider("Hostel Gym","gym", R.drawable.ic_gym);
        provider.add(p1);
        p1 = new ContactProvider("Student Library","library", R.drawable.ic_library);
        provider.add(p1);
    }
}



