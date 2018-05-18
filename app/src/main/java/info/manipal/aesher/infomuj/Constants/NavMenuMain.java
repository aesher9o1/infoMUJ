package info.manipal.aesher.infomuj.Constants;

import java.util.List;

import info.manipal.aesher.infomuj.Adapters.ClubProvider;

public class NavMenuMain {

    public void engineering(List<ClubProvider> provider){

        ClubProvider p1 = new ClubProvider("C","CSE","NaN",false,-1);
        provider.add(p1);
        p1 = new ClubProvider("I","IT","NaN",false,-1);
        provider.add(p1);
        p1 = new ClubProvider("C","CCE","NaN",false,-1);
        provider.add(p1);
        p1 = new ClubProvider("E","Electrical","NaN",false,-1);
        provider.add(p1);
        p1 = new ClubProvider("E","ECE","NaN",false,-1);
        provider.add(p1);
        p1 = new ClubProvider("M","Mechanical","NaN",false,-1);
        provider.add(p1);
        p1 = new ClubProvider("A","Automobile","NaN",false,-1);
        provider.add(p1);
        p1 = new ClubProvider("M","Mechatronics","NaN",false,-1);
        provider.add(p1);
        p1 = new ClubProvider("C","Chemical","NaN",false,-1);
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
}
