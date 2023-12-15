package app.integration.util;
import app.integration.models.dto.Page;
import java.util.ArrayList;
import java.util.List;

public class PageUtil {
    public static List<Page> pagination(long limit, long offset, long size) {
        List<Page> res = new ArrayList();
        long cnt = size / limit;
        if (0L < size % limit) {
            ++cnt;
        }

        long current = offset / limit;
        if (9L >= cnt) {
            for(int i = 0; (long)i < cnt; ++i) {
                res.add(new Page((i + 1), (long)i * limit, (long)i == current));
            }
        } else {
            long n = offset / limit;
            if (3L <= n) {
                res.add(new Page(1L, 0L, 0L == current));
            }

            if (4L <= n) {
                res.add(new Page(0L, 0L, false));
            }

            for(long o = -2L; 2L >= o; ++o) {
                long on = o + n;
                if (0L <= on && on < cnt) {
                    res.add(new Page(on + 1L, on * limit, on == current));
                }
            }

            if (n + 4L < cnt) {
                res.add(new Page(0L, 0L, false));
            }

            if (n + 3L < cnt) {
                res.add(new Page(cnt, (cnt - 1L) * limit, cnt - 1L == current));
            }
        }

        return res;
    }

    public static long prepare_offset(Long offset) {
        return null == offset ? 0L : offset;
    }

}
