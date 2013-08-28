package org.zechocapic.mynicecinoche;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.util.TypedValue;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class RecupInfosAsyncTask extends AsyncTask<String, Void, Document>{
	private FilmFragment filmFragment;
	
	public RecupInfosAsyncTask(FilmFragment filmFragment) {
		this.filmFragment = filmFragment;
	}

	@Override
	protected Document doInBackground(String... urls) {
		Document document = null;
		document = Jsoup.parse(urls[0]);
		return document;
	}

    // parsing web page and drawing the result
	@Override
    protected void onPostExecute(Document doc) {
		// toast to manage failed connection
        if(doc == null){
        	Toast.makeText(filmFragment.getActivity(), "Erreur : le chargement de la page n'aboutit pas !", Toast.LENGTH_SHORT).show();;
            return;
        }
        
        // style variables
        int titleTextSize, subtitleTextSize, simpleTextSize;
        int titleBackgroundColor, subtitleBackgroundColor, simpleBackgroundColor;
        int titleTextColor, subtitleTextColor, simpleTextColor;
        
        // initializing styles
        int appStyle = 1;
        if (appStyle == 1) {
        	titleTextSize = 20;
        	subtitleTextSize = 16;
        	simpleTextSize = 14;
        	titleBackgroundColor = Color.rgb(47, 55, 64);
        	subtitleBackgroundColor = Color.rgb(192, 192, 192);
        	simpleBackgroundColor = Color.rgb(224, 224, 224);
        	titleTextColor = Color.WHITE;
        	subtitleTextColor = Color.BLACK;
        	simpleTextColor = Color.BLACK;
        } else {
        	titleTextSize = 32;
        	subtitleTextSize = 24;
        	simpleTextSize = 24;
        	titleBackgroundColor = Color.BLACK;
        	subtitleBackgroundColor = Color.rgb(32, 32, 32);
        	simpleBackgroundColor = Color.rgb(64, 64, 64);
        	titleTextColor = Color.WHITE;
        	subtitleTextColor = Color.WHITE;
        	simpleTextColor = Color.WHITE;
        }
        
		// page layout
        LinearLayout linearLayout = (LinearLayout) filmFragment.getActivity().findViewById(R.id.layout_film);
        linearLayout.setBackgroundColor(Color.WHITE);
        linearLayout.setPadding(5, 5, 5, 5);
        linearLayout.removeAllViews();
        
		// movie title
        TextView twTitre = new TextView(filmFragment.getActivity());
        twTitre.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        twTitre.setBackgroundColor(titleBackgroundColor);
        twTitre.setTextSize(TypedValue.COMPLEX_UNIT_SP, titleTextSize);
        twTitre.setTypeface(null, Typeface.BOLD);
        twTitre.setTextColor(titleTextColor);
        twTitre.setText(doc.select("div.titre").first().text());
        linearLayout.addView(twTitre);
		
		// horizontal layout containing poster and info
        LinearLayout layoutAfficheInfos = new LinearLayout(filmFragment.getActivity());            			
        layoutAfficheInfos.setOrientation(LinearLayout.HORIZONTAL);
        layoutAfficheInfos.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        layoutAfficheInfos.setBackgroundColor(Color.BLACK);
		
		// poster
		ImageView imAffiche = new ImageView(filmFragment.getActivity());
		imAffiche.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 0f));
		layoutAfficheInfos.addView(imAffiche);
        new RecupImageAsyncTask(imAffiche).execute(doc.select("div.poster").first().text());
        
        // infos
        TextView twInfosDiverses = new TextView(filmFragment.getActivity());
        twInfosDiverses.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1f));
        twInfosDiverses.setBackgroundColor(simpleBackgroundColor);
        twInfosDiverses.setTextSize(TypedValue.COMPLEX_UNIT_SP, simpleTextSize);
        twInfosDiverses.setTextColor(simpleTextColor);
        twInfosDiverses.setText(doc.select("div.infos").first().text().replace("| Sortie cinéma" , "").replace("Réalisé par", "\nRéalisé par").replace("Avec :", "\nAvec :").replace("Durée", "\nDurée").replace("Pays", "\nPays").replace("Presse", "\nPresse"));
        layoutAfficheInfos.addView(twInfosDiverses);
        
        linearLayout.addView(layoutAfficheInfos);
        
        // synopsis
		TextView twCadreSynopsis = new TextView(filmFragment.getActivity());
		twCadreSynopsis.setTypeface(null, Typeface.BOLD_ITALIC);
		twCadreSynopsis.setBackgroundColor(subtitleBackgroundColor);
		twCadreSynopsis.setTextSize(TypedValue.COMPLEX_UNIT_SP, subtitleTextSize);
		twCadreSynopsis.setTextColor(subtitleTextColor);
		twCadreSynopsis.setText("Synopsis");
		linearLayout.addView(twCadreSynopsis);
		
		// description
        TextView twDesc = new TextView(filmFragment.getActivity());
        twDesc.setBackgroundColor(simpleBackgroundColor);
        twDesc.setTextSize(TypedValue.COMPLEX_UNIT_SP, simpleTextSize);
        twDesc.setTextColor(simpleTextColor);
        twDesc.setText(doc.select("div.desc").first().text());
        linearLayout.addView(twDesc);
        
    }
}
