document.getElementById('shortenForm').addEventListener('submit', async function(e) {
  e.preventDefault();
  const url = document.getElementById('urlInput').value;
  const res = await fetch('/shorten', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ url })
  });
  const data = await res.json();
  document.getElementById('result').textContent = `Short URL: ${data.shortUrl}`;
});