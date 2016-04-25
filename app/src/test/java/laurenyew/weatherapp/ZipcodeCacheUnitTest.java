package laurenyew.weatherapp;

import android.content.Context;
import android.content.SharedPreferences;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import laurenyew.weatherapp.cache.ZipcodeCache;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

/**
 * Test Zipcode Cache
 */
@RunWith(MockitoJUnitRunner.class)
public class ZipcodeCacheUnitTest {

    private static final String APP_NAME = "laurenyew.weatherapp";
    private static final String ZIPCODE_CACHE_KEY = "zipcode_cache";
    private static final String[] DEFAULT_ZIPCODES = {"75078", "78757", "92127"};
    private static ArrayList<String> expectedZipcodes;

    @Mock
    private Context mMockContext;

    @Mock
    private SharedPreferences mSharedPreferences;

    @Mock
    private SharedPreferences.Editor mSharedPreferencesEditor;

    private ZipcodeCache mCache;

    @Before
    public void setup() {

        expectedZipcodes = new ArrayList<>();
        expectedZipcodes.addAll(Arrays.asList(DEFAULT_ZIPCODES));

        when(mMockContext.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE))
                .thenReturn(mSharedPreferences);
        when(mSharedPreferences.edit())
                .thenReturn(mSharedPreferencesEditor);
        mCache = ZipcodeCache.getInstance();

        //Check cache
        assertEquals(0, mCache.size());

        //Check shared preferences
        Set<String> savedCache = mSharedPreferences.getStringSet(ZIPCODE_CACHE_KEY, new HashSet<String>());
        assertEquals(0, savedCache.size());

        //Init cache
        mCache.initListCacheAndSharedPreferences(mMockContext);

        //Check cache after initialization
        assertEquals(3, mCache.size());
    }

    /**
     * Clear the cache on teardown
     */
    @After
    public void teardown() {
        mCache.clear(mMockContext);
    }

    @Test
    public void testInitListOnEmptyPreferencesSetsCorrectDefaults() throws Exception {
        compareCacheWithExpectedZipcodes();
    }

    @Test
    public void testAddZipcodeUpdatesCacheInCorrectOrder() throws Exception {
        String newZipcodeInFront = "11111";
        expectedZipcodes.add(0, newZipcodeInFront);
        mCache.addZipcode(mMockContext, newZipcodeInFront);
        compareCacheWithExpectedZipcodes();

        String newZipcodeAtEnd = "99999";
        expectedZipcodes.add(newZipcodeAtEnd);
        mCache.addZipcode(mMockContext, newZipcodeAtEnd);
        compareCacheWithExpectedZipcodes();
    }

    @Test
    public void testClearZipcodeCache() {
        expectedZipcodes.clear();
        mCache.clear(mMockContext);
        compareCacheWithExpectedZipcodes();
    }

    private void compareCacheWithExpectedZipcodes() {
        assertEquals(expectedZipcodes.size(), mCache.size());
        for (int i = 0; i < expectedZipcodes.size(); i++) {
            assertThat(mCache.getItem(i), is(expectedZipcodes.get(i)));
        }
    }
}