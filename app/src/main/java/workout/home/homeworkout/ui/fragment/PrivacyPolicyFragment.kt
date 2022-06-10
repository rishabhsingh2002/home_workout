package workout.home.homeworkout.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import workout.home.homeworkout.R
import workout.home.homeworkout.databinding.FragmentPrivacyPolicyBinding
import java.net.URL

class PrivacyPolicyFragment : Fragment() {
    private var _binding: FragmentPrivacyPolicyBinding? = null

    private val binding get() = _binding!!

    private val url = "file:///android_asset/privacy_policy.html"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPrivacyPolicyBinding.inflate(inflater, container, false)

        //Toolbar Setup
        toolBarSetUp()

        //WebView setup
        webViewSetup(url)


        return binding.root

    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun webViewSetup(webUrl: String) {
        //add the privacy policy file in asset folder
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.loadUrl(webUrl)
    }

    private fun toolBarSetUp() {
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarPrivacyPolicy)
        if ((activity as AppCompatActivity).supportActionBar != null) {
            (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
            (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white)
            binding.toolbarPrivacyPolicy.title = "Privacy Policy"
        }
        binding.toolbarPrivacyPolicy.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}