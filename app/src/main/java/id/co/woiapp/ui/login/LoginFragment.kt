package id.co.woiapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import id.co.woiapp.R
import id.co.woiapp.data.entities.User
import id.co.woiapp.databinding.FragmentLoginBinding
import id.co.woiapp.utils.*

@AndroidEntryPoint
class LoginFragment : Fragment() {

    companion object {
        private const val TAG = "GoogleActivity"
        private const val RC_SIGN_IN = 9001
    }

    private var binding: FragmentLoginBinding by autoCleared()
    private val viewModel: LoginViewModel by viewModels()
    private lateinit var auth: FirebaseAuth
    private lateinit var customBackpressed: OnBackPressedCallback

    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        checkLogInUser()
    }

    fun checkLogInUser(){
        if(SharedPref.isLogin(requireActivity())){
            customBackpressed.remove()
            findNavController().navigate(
                R.id.action_loginFragment_to_homeFragment)
        }
    }

    fun saveLogInUser(user:User){
        if(user.idUser != null){
            SharedPref.setLogin(true,requireActivity())
            SharedPref.saveStringVal(SharedPref.EMAIL,user.email?:"",requireActivity())
            SharedPref.saveStringVal(SharedPref.NAME,user.name?:"",requireActivity())
            SharedPref.saveStringVal(SharedPref.PHOTO,user.photo?:"",requireActivity())
            SharedPref.saveIntVal(SharedPref.IDUSER,user.idUser,requireActivity())
            SharedPref.saveStringVal(SharedPref.TOKEN,user.token?:"",requireActivity())
            checkLogInUser()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogin.setOnClickListener { signIn() }
        GenerateView.loading(binding.loading,requireContext())
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.loginUser.observe(viewLifecycleOwner, Observer {
            when (it.status){
                Resource.Status.SUCCESS -> {
                    Log.d("dasddasgasdbgasdasc", it.data?.get(0).toString())
                    if(it.data != null)
                        if (it.data.isNotEmpty())
                            saveLogInUser(it.data[0])
                    binding.loading.visibility = View.INVISIBLE

                }
                Resource.Status.ERROR -> {
                    SnackBin.show(binding.root,it.message.toString())
//                    SnackAlert.success(view, "success")
                    binding.loading.visibility = View.INVISIBLE
                }

                Resource.Status.LOADING ->
                    binding.loading.visibility = View.VISIBLE
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        customBackpressed = requireActivity().onBackPressedDispatcher.addCallback {
            requireActivity().finish()
        }
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        auth = Firebase.auth
    }


    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
                // ...
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    Log.d("dadgashdgasdasddas",user?.email)
                    if(user != null)
                    viewModel.login(User(user.displayName,"",null,user.email,""))

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    // ...
//                    Snackbar.make(view, "Authentication Failed.", Snackbar.LENGTH_SHORT).show()
//                    updateUI(null)
                }

                // ...
            }
    }


}