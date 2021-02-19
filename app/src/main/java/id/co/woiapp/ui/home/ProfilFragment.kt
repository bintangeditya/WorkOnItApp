package id.co.woiapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import id.co.woiapp.R
import id.co.woiapp.databinding.FragmentProfilBinding
import id.co.woiapp.utils.GenerateView
import id.co.woiapp.utils.SharedPref
import id.co.woiapp.utils.autoCleared


class ProfilFragment : Fragment() {

    private var binding : FragmentProfilBinding by autoCleared()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfilBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        GenerateView.loading(binding.loadingBlocker, requireContext())
        binding.tvName.text = SharedPref.getStringVal(SharedPref.NAME,requireActivity())
        binding.tvEmail.text = SharedPref.getStringVal(SharedPref.EMAIL,requireActivity())
        binding.btnLogout.setOnClickListener {
            logout()
        }
        binding.btnBack.setOnClickListener { findNavController().navigateUp() }
    }

    private fun logout(){
        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()

        val googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
        googleSignInClient.signOut().addOnSuccessListener {
            SharedPref.logout(requireActivity())
            findNavController().navigate(R.id.action_profilFragment_to_loginFragment)
        }

    }

}