(function getTiming() {
    try {
        var time = performance.timing;
        var timingObj = {};
        var loadTime = (time.loadEventEnd - time.loadEventStart) / 1000;
        if (loadTime < 0) {
            setTimeout(function () {
                getTiming();
            }, 200);
            return;
        }
        timingObj['redirect'] = (time.redirectEnd - time.redirectStart) / 1000;
        timingObj['DNS lookup'] = (time.domainLookupEnd - time.domainLookupStart) / 1000;
        timingObj['TCP handshake'] = (time.connectEnd - time.connectStart) / 1000;
        timingObj['HTTP request-response'] = (time.responseEnd - time.requestStart) / 1000;
        timingObj['DOM before-ready'] = (time.responseEnd - time.navigationStart) / 1000;
        timingObj['DOM load'] = (time.domComplete - time.domLoading) / 1000;
        timingObj['DOM struct-parse'] = (time.domInteractive - time.domLoading) / 1000;
        timingObj['scrpit-load'] = (time.domContentLoadedEventEnd - time.domContentLoadedEventStart) / 1000;
        timingObj['onload event'] = (time.loadEventEnd - time.loadEventStart) / 1000;
        timingObj['page all cost'] = (timingObj['redirect'] + timingObj['DNS lookup'] + timingObj['TCP handshake'] + timingObj['HTTP request-response'] + timingObj['DOM struct-parse'] + timingObj['DOM load']);
        for (item in timingObj) {
            console.log(item + ":" + timingObj[item] + '(ms)');
        }
        console.log(performance.timing);
    } catch (e) {
        console.log(timingObj)
        console.log(performance.timing);
    }
})()