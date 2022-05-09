FROM python:3.10
EXPOSE 5000
WORKDIR /app
ADD app.py .
ADD requirements.txt .
RUN pip install -r requirements.txt
ENTRYPOINT ["python"]
CMD ["./app.py"]

