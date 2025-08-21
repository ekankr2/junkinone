const env = {
    mode: process.env.NODE_ENV,
    BASE_URL:
        process.env.NODE_ENV === 'production'
            ? 'https://junkinone.com'
            : 'http://localhost:3000',
    CDN_URL: 'https://cdn.junkinone.com',
    DB_URL: 'https://junkinone.com',
};

export default env;
