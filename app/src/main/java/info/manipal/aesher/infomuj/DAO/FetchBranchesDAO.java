package info.manipal.aesher.infomuj.DAO;

import android.util.Log;

import com.google.common.util.concurrent.SettableFuture;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;


public class FetchBranchesDAO {
    private static FetchBranchesDAO instance;
    private static DatabaseReference branchTable;

    private FetchBranchesDAO() {
        branchTable = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("branches");
    }

    public static FetchBranchesDAO getInstance() {
        if (instance == null) {
            instance = new FetchBranchesDAO();
        }
        return instance;
    }

    public Future<Map<String, Branch>> getBranches() {
        final SettableFuture<Map<String, Branch>> matchedBranchesFuture = SettableFuture.create();
        branchTable.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
//                    Log.w("Okay","Okay");
                    Map<String, Branch> retrievedBranches = new HashMap<>();
                    for (DataSnapshot branch : dataSnapshot.getChildren()) {

                        Branch branchValue = branch.getValue(Branch.class);
                        retrievedBranches.put(branch.getKey(), branchValue);
//                        Log.w("DAO", branchValue.name);
                    }
                    matchedBranchesFuture.set(retrievedBranches);
                } else {
                    matchedBranchesFuture.set(null);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                matchedBranchesFuture.set(null);
            }
        });

        return matchedBranchesFuture;
    }

}
