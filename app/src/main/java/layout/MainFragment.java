package layout;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;
import com.wiberg.facebookloginapp.R;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private static final String MY_TAG = "jonahe";

    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private FacebookCallback<LoginResult> loginCallback;
    private TextView lblProfileDetails;
    private ProfilePictureView profilePictureView;
    // private ImageView imgvProfilePic;


    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        displayNameAndPic();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        // Initialize the SDK before executing any other operations,
        // especially, if you're using Facebook UI elements.

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loginButton = (LoginButton) view.findViewById(R.id.login_button);
        loginButton.setReadPermissions("user_friends");
        // If using in a fragment
        loginButton.setFragment(this);
        // Other app specific specialization

        // Callback registration
        callbackManager = CallbackManager.Factory.create();

        loginCallback = new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code

                AccessToken accessToken = loginResult.getAccessToken();
                displayNameAndPic();

            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Log.d(MY_TAG, exception.getMessage());
            }
        };


        loginButton.registerCallback(callbackManager, loginCallback);

    }

    @Nullable
    private void displayNameAndPic() {
        Profile profile = Profile.getCurrentProfile();
        // may be null if something went wrong
        if(profile != null) {
            lblProfileDetails = (TextView) getView().findViewById(R.id.lbl_profileDetails);
            lblProfileDetails.setText(profile.getName());

            profilePictureView = (ProfilePictureView) getView().findViewById(R.id.profilePicture);
            profilePictureView.setProfileId(profile.getId());


/*            imgvProfilePic = (ImageView) getView().findViewById(R.id.imgvProfilePic);
            Log.d(MY_TAG, profile.getProfilePictureUri(64, 64).toString());
            URL profile_img_URL = null;
            try {
                profile_img_URL = new URL("" + profile.getProfilePictureUri(50,100).toString());
            } catch( MalformedURLException e) {
                Log.d(MY_TAG, e.getMessage());
            }

            Bitmap pic = null;
            try {
                Log.d(MY_TAG, profile_img_URL.toString());
                pic = BitmapFactory.decodeStream(profile_img_URL.openConnection().getInputStream());
            } catch(IOException e) {
                Log.d(MY_TAG, e.getMessage());
            } catch (NullPointerException f) {
                Log.d(MY_TAG, f.getMessage());
            }

            imgvProfilePic.setImageBitmap(pic);*/





        } else {
            Log.d(MY_TAG, "Profile = null");
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }
}
