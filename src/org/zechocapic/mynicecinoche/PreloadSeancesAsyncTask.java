package org.zechocapic.mynicecinoche;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import android.os.AsyncTask;
import android.widget.LinearLayout;

public class PreloadSeancesAsyncTask extends AsyncTask<String, Void, Document> {
	private LinearLayout layoutBloc;
	
	public PreloadSeancesAsyncTask(LinearLayout layoutBloc) {
		super();
		this.layoutBloc = layoutBloc;
	}

	@Override
	protected Document doInBackground(String... urls) {
		Document document = null;
		String url = urls[0];
		try {
			document = Jsoup.connect(url).get();
			return document;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

    // parsing web page and drawing the result
	@Override
    protected void onPostExecute(Document doc) {
		
		// toast to manage failed connection
        if(doc == null){
            return;
        }
        
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<html><head><title>First parse</title></head>\n");
        stringBuilder.append("<body>body\n");
        
		// movie title
		String titre = doc.select("div.bloc_rub_titre\n").first().text();
		stringBuilder.append("<div class=\"bloc_rub_titre\">\n");
		stringBuilder.append(titre + "\n");
		stringBuilder.append("</div>\n");
		
        // textView for published date 
        /*Element genre = doc.select("div.infos [itemprop=genre]").first();
        Element dateSortie = doc.select("div.infos [itemprop=datePublished]").first();
        Element realisateur = doc.select("div.infos [itemprop=director] [itemprop=name]").first();
        Element acteur = doc.select("div.infos [itemprop=actors]").first();
        Element duree = doc.select("div.infos [itemprop=duration]").first();
        twInfosDiverses.setText("Genre : " + genre.text() + "\n" +
        		"Date de sortie : " + dateSortie.text() + "\n" +
        		"Durée : " + duree.text() + "\n" +
        		"Réalisateur : " + realisateur.text() + "\n" +
        		"Acteurs : " + acteur.text().replace("Avec :", "").replace("...> Tout le casting", ""));
        layoutAfficheInfos.addView(twInfosDiverses);
        
        linearLayout.addView(layoutAfficheInfos);
        
        // picture frame for Synopsis
		TextView twCadreSynopsis = new TextView(filmFragment.getActivity());
		twCadreSynopsis.setTypeface(null, Typeface.BOLD_ITALIC);
		twCadreSynopsis.setBackgroundColor(subtitleBackgroundColor);
		twCadreSynopsis.setTextSize(TypedValue.COMPLEX_UNIT_SP, subtitleTextSize);
		twCadreSynopsis.setTextColor(subtitleTextColor);
		twCadreSynopsis.setText("Synopsis");
		linearLayout.addView(twCadreSynopsis);
		
		// textView for synopsis
		Element description = doc.select("div.description_cnt").first();
        TextView twDesc = new TextView(filmFragment.getActivity());
        twDesc.setBackgroundColor(simpleBackgroundColor);
        twDesc.setTextSize(TypedValue.COMPLEX_UNIT_SP, simpleTextSize);
        twDesc.setTextColor(simpleTextColor);
        twDesc.setText(description.text());
        linearLayout.addView(twDesc);*/
        stringBuilder.append("</body></html>");
        String layoutTag = stringBuilder.toString();
        layoutBloc.setTag(layoutTag);
        
    }
	
}
